package com.bridgelabz.parkinglotdetection;

import com.bridgelabz.parkinglotdetection.enums.CarType;
import com.bridgelabz.parkinglotdetection.enums.UserType;
import com.bridgelabz.parkinglotdetection.parkingspace.CarParking;
import com.bridgelabz.parkinglotdetection.parkingspace.operations.GetParkingCarDetails;
import com.bridgelabz.parkinglotdetection.user.User;
import com.bridgelabz.parkinglotdetection.vehicle.Car;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.bridgelabz.parkinglotdetection.enums.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.enums.CarType.SMALLCAR;
import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingLotDetectionTest {

    /*
        @desc : Create a new user with the given parameters.
        @params :
            userId: String - ID of the user
            carId: String - ID of the car
            brand: String - Brand of the car
            color: String - Color of the car
            carType: CarType - Type of the car (enum)
       @return : User
    */
    public User createNormalUser(String userId, String carId, String brand, String color, CarType carType) {
        Car car = new Car(carId, brand, color, carType);
        return new User(userId, car);
    }

    /*
        @desc : Create a new user with a disability status and the given parameters.
        @params :
            userId: String - ID of the user
            userType: UserType - Type of user (disability status)
            carId: String - ID of the car
            brand: String - Brand of the car
            color: String - Color of the car
            carType: CarType - Type of the car (enum)
        @return : User
    */
    public User createDisabilityUser(String userId, UserType userType, String carId, String brand, String color, CarType carType) {
        Car car = new Car(carId, brand, color, carType);
        return new User(userId, car, userType);
    }

    /*
    @desc : it will create a 3 parking lot with each size 1 and park the cars in them
    @params : no params
    @return : no return
     */
    public void fillTheParkingLotWithCars(){
        CarParking carParking = new CarParking(3 , 1);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        assertParkCar(user, "Mahidhar1", "car1");
        user = createNormalUser("Mahidhar2" , "car2", "Benze", "white", SMALLCAR);
        assertParkCar(user, "Mahidhar2", "car2");
        user = createNormalUser("Mahidhar3" , "car3", "BMW", "black", LARGECAR);
        assertParkCar(user, "Mahidhar3", "car3");
    }
    /*
     * @desc : Tests parking a car by asserting parking-related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    public void assertParkCar(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = GetParkingCarDetails.getCarParkingPositions();
        OptionalInt rowIndex = findRowIndex(carParkingPositions);
        rowIndex.ifPresent(i -> {
            int columnIndex = carParkingPositions.get(i).indexOf(true);
            user.parkCarAtIndex(i, columnIndex);
        });
        System.out.println(user);
        System.out.println(CarParking.multipleParkingLots);
        assertTrue(GetParkingCarDetails.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Tests unparking a car by asserting unparking-related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    public void assertUnparkCar(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = GetParkingCarDetails.getCarParkingPositions();
        int[] parkingIndices = getParkingIndices(carParkingPositions);
        int rowIdx = parkingIndices[0];
        int colIdx = parkingIndices[1];
        user.parkCarAtIndex(rowIdx, colIdx);
        assertTrue(GetParkingCarDetails.isMyCarParkedInParkingLot(username, carId));
        user.unParkMyCar();
        assertFalse(GetParkingCarDetails.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Tests unparking a car by providing its position and asserting related functionalities.
     * @param user User object representing the car owner
     * @param username Username of the user
     * @param carId ID of the car
     * @return : void
     */
    public void assertUnparkCarWithPosition(User user, String username, String carId) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = GetParkingCarDetails.getCarParkingPositions();
        int[] parkingIndices = getParkingIndices(carParkingPositions);
        int rowIdx = parkingIndices[0];
        int colIdx = parkingIndices[1];
        user.parkCarAtIndex(rowIdx, colIdx);
        assertTrue(GetParkingCarDetails.isMyCarParkedInParkingLot(username, carId));
        user.unParkMyCar(rowIdx, colIdx);
        assertFalse(GetParkingCarDetails.isMyCarParkedInParkingLot(username, carId));
    }
    /*
     * @desc : Finds the index of the row where a parking space is available.
     * @param carParkingPositions List of parking positions
     * @return Optional index of the available row
     */
    public OptionalInt findRowIndex(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        return IntStream.range(0, carParkingPositions.size())
                .filter(i -> carParkingPositions.get(i).stream().anyMatch(Boolean::booleanValue))
                .findFirst();
    }
    /*
     * @desc : Finds the indices (row and column) of an available parking space.
     * @param carParkingPositions List of parking positions
     * @return Array containing row and column indices of the available parking space
     */
    public int[] getParkingIndices(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        OptionalInt rowIndex = findRowIndex(carParkingPositions);
        return rowIndex.isPresent() ? new int[]{rowIndex.getAsInt(), carParkingPositions.get(rowIndex.getAsInt()).indexOf(true)} : new int[]{-1, -1};
    }

}
