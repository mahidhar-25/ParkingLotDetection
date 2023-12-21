package com.bridgelabz.parkinglotdetection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.bridgelabz.parkinglotdetection.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.CarType.SMALLCAR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ParkingLotDetectionTest {

    /*
    @desc : this test case validates whether an user can park or not , if there is available position to park ,
     park the car

     */
    @Test
    public void UserCanParkHisCar() {
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar", car);
        assertParkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void UserCanUnParkHisCar() {
        Car car = new Car("car1", "BMW", "white", SMALLCAR);
        User user = new User("Mahidhar", car);
        assertUnparkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void UserCanUnParkHisCarByGivingPositionOfCar() {
        Car car = new Car("car1", "BMW", "white", SMALLCAR);
        User user = new User("Mahidhar", car);
        assertUnparkCarWithPosition(user, "Mahidhar", "car1");
    }

    @Test
    public void OwnerShouldKnowParkingLotIsFullOrNot(){
        int indexOfParkingLot = 1;
      assertFalse(CarParking.isParkingLotFull(indexOfParkingLot));
    }
    /*
     * @desc : Tests parking a car by asserting parking-related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    private void assertParkCar(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();
        OptionalInt rowIndex = findRowIndex(carParkingPositions);
        rowIndex.ifPresent(i -> {
            int columnIndex = carParkingPositions.get(i).indexOf(true);
            user.parkCarAtIndex(i, columnIndex);
        });
        System.out.println(user);
        System.out.println(CarParking.multipleParkingLots);
        assertTrue(CarParking.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Tests unparking a car by asserting unparking-related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    private void assertUnparkCar(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();
        int[] parkingIndices = getParkingIndices(carParkingPositions);
        int rowIdx = parkingIndices[0];
        int colIdx = parkingIndices[1];
        user.parkCarAtIndex(rowIdx, colIdx);
        assertTrue(CarParking.isMyCarParkedInParkingLot(username, carId));
        user.unParkMyCar();
        assertFalse(CarParking.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Tests unparking a car by providing its position and asserting related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    private void assertUnparkCarWithPosition(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();
        int[] parkingIndices = getParkingIndices(carParkingPositions);
        int rowIdx = parkingIndices[0];
        int colIdx = parkingIndices[1];
        user.parkCarAtIndex(rowIdx, colIdx);
        assertTrue(CarParking.isMyCarParkedInParkingLot(username, carId));
        user.unParkMyCar(rowIdx, colIdx);
        assertFalse(CarParking.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Finds the index of the row where a parking space is available.
     * @param carParkingPositions List of parking positions
     * @return Optional index of the available row
     */
    private OptionalInt findRowIndex(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        return IntStream.range(0, carParkingPositions.size())
                .filter(i -> carParkingPositions.get(i).stream().anyMatch(Boolean::booleanValue))
                .findFirst();
    }
    /*
     * @desc : Finds the indices (row and column) of an available parking space.
     * @param carParkingPositions List of parking positions
     * @return Array containing row and column indices of the available parking space
     */
    private int[] getParkingIndices(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        OptionalInt rowIndex = findRowIndex(carParkingPositions);
        return rowIndex.isPresent() ? new int[]{rowIndex.getAsInt(), carParkingPositions.get(rowIndex.getAsInt()).indexOf(true)} : new int[]{-1, -1};
    }

}
