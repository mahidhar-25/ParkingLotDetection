package com.bridgelabz.parkinglotdetection;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.bridgelabz.parkinglotdetection.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.CarType.SMALLCAR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotDetectionTest {

//    @Before
//    public void setup(){
//        CarParking carParking = new CarParking();
//    }

    /*
    @desc : this test case validates whether an user can park or not , if there is available position to park ,
     park the car

     */
    @Test
    public void A_UserCanParkHisCar() {
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar", car);
        assertParkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void B_UserCanUnParkHisCar() {
        Car car = new Car("car1", "BMW", "white", SMALLCAR);
        User user = new User("Mahidhar", car);
        assertUnparkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void C_UserCanUnParkHisCarByGivingPositionOfCar() {
        Car car = new Car("car1", "BMW", "white", SMALLCAR);
        User user = new User("Mahidhar", car);
        assertUnparkCarWithPosition(user, "Mahidhar", "car1");
    }

    @Test
    public void D_OwnerShouldKnowParkingLotIsFullOrNot(){
        int indexOfParkingLot = 6;
      assertFalse(CarParking.isParkingLotFull(indexOfParkingLot));
    }


    @Test
    public void E_AirportAuthorityShouldKnow_WhenCompleteParkingLotIsFull(){
        assertFalse(CarParking.isFull());
        fillTheParkingLotWithCars();
        assertTrue(CarParking.isFull());
    }


    @Test
    public void F_ParkingOwnerShouldKnowWhenThereIsAnSpaceInParkingLot(){
        fillTheParkingLotWithCars();
        assertTrue(CarParking.isFull());
        CarParking.removeMyCarFromParkingLot("Mahidhar1", "car1");
        assertFalse(CarParking.isFull());
    }

    @Test
    public void G_ParkingAttendantCanDecideWhereToParkCar(){
        CarParking carParking = new CarParking(2);
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar1", car);
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        parkingAttendant.parkUserCar(user);
        System.out.println(CarParking.multipleParkingLots);
        assertTrue(CarParking.isMyCarParkedInParkingLot("Mahidhar1" , "car1"));
    }

    /*
    @desc : it will create a 3 parking lot with each size 1 and park the cars in them
    @params : no params
    @return : no return
     */
    public void fillTheParkingLotWithCars(){
        CarParking carParking = new CarParking(3 , 1);
        //user 1
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar1", car);
        assertParkCar(user, "Mahidhar1", "car1");
        //user 2
        car = new Car("car2", "Benze", "white", SMALLCAR);
        user = new User("Mahidhar2", car);
        assertParkCar(user, "Mahidhar2", "car2");
        //user 3
        car = new Car("car3", "BMW", "black", LARGECAR);
        user = new User("Mahidhar3", car);
        assertParkCar(user, "Mahidhar3", "car3");
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
