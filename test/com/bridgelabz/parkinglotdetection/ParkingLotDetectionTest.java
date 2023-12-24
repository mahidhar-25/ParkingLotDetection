package com.bridgelabz.parkinglotdetection;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.bridgelabz.parkinglotdetection.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.CarType.SMALLCAR;
import static com.bridgelabz.parkinglotdetection.UserType.HANDICAP;
import static org.junit.Assert.*;

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

    @Test
    public void H_UserNeedToFindThePositionOfTheCar(){
        CarParking carParking = new CarParking(3 , 1);
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar1", car);
        new ParkingAttendant().parkUserCar(user);
        int[] positions = CarParking.getMyCarParkingPosition("Mahidhar1" , "car1");
        assertArrayEquals(new int[]{2 , 0} , positions);
    }

    @Test
    public void I_OwnerShouldGetTheParkingTimeOfTheCar(){
        CarParking carParking = new CarParking(2);
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar1", car);
        new ParkingAttendant().parkUserCar(user);
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        int[] positions = CarParking.getMyCarParkingPosition("Mahidhar1" , "car1");
        user = CarParking.getCarUserDetailsParkedAt(positions[0], positions[1]);
        LocalDateTime parkingTimeOfMyCar = user.getCar().getParkingTime();
        Duration timeDifference = Duration.between(expectedParkingTime, parkingTimeOfMyCar);
        long maxAllowedDifferenceInSeconds = 60;
        assertTrue(timeDifference.getSeconds() <= maxAllowedDifferenceInSeconds);
    }


    @Test
    public void J_ParkingAttendantShouldParkCarEvenly(){
        CarParking carParking = new CarParking(3);
        Car car = new Car("car1", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar1", car);
        user.parkCarAtIndex(0 , 0);
        car = new Car("car2", "BMW", "white", LARGECAR);
         user = new User("Mahidhar2", car);
        user.parkCarAtIndex(1 , 0);
        car = new Car("car3", "BMW", "white", LARGECAR);
        user = new User("Mahidhar3", car);
        new ParkingAttendant().parkUserCarEvenly(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(2 , 0);
        assertTrue(user.equals(user1));
    }


    @Test
    public void K_HandicapCarShouldParkAtNearestPositionEvenItIsLargeCar(){
        CarParking carParking = new CarParking(3);
        Car car = new Car("car2", "BMW", "white", LARGECAR);
        User user = new User("Mahidhar2", car , HANDICAP);
        new ParkingAttendant().parkUserCar(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(0 , 0);
        assertTrue(user.equals(user1));
    }


    @Test
    public void L_ParkingAttendantShouldParkCarsWhereMoreSpaceIsAvailable(){
        CarParking carParking = new CarParking(2);
        Car car = new Car("car1", "BMW", "white", SMALLCAR);
        User user = new User("Mahidhar1", car);
        user.parkCarAtIndex(0 , 0);
        car = new Car("car2", "BMW", "white", SMALLCAR);
        user = new User("Mahidhar2", car);
        user.parkCarAtIndex(0 , 1);
        car = new Car("car5", "BMW", "white", LARGECAR);
        user = new User("Mahidhar5", car);
        user.parkCarAtIndex(1 , 5);
        car = new Car("car3", "BMW", "white", LARGECAR);
        user = new User("Mahidhar3", car);
        new ParkingAttendant().parkUserLargeCarAtMoreSpaceAvailable(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(1 , 6);
        System.out.println(Arrays.toString(CarParking.getMyCarParkingPosition("Mahidhar3", "car3")));
        assertTrue(user.equals(user1));
    }


    @Test
    public void M_GetAllThePositionsOfTheSpecificColorCarDetails(){
        CarParking carParking = new CarParking(2);
        Car car = new Car("car1", "BMW", "black", SMALLCAR);
        User user = new User("Mahidhar1", car);
        user.parkCarAtIndex(0 , 0);
        car = new Car("car2", "BMW", "white", SMALLCAR);
        user = new User("Mahidhar2", car);
        user.parkCarAtIndex(0 , 1);
        car = new Car("car5", "BMW", "white", LARGECAR);
        user = new User("Mahidhar5", car);
        user.parkCarAtIndex(1 , 5);
        car = new Car("car3", "BMW", "white", LARGECAR);
        user = new User("Mahidhar3", car);
        new ParkingAttendant().parkUserCar(user);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        ArrayList<User> allWhiteColoredUserCars = policeOfficer.getAllSpecificColorCarUsers("white");
        System.out.println(allWhiteColoredUserCars);
        ArrayList<int[]> positions = policeOfficer.getAllPositionsOfUsers(allWhiteColoredUserCars);
//        for(int[]a : positions) {
//            System.out.println(Arrays.toString(a));
//        }
        assertArrayEquals(new int[]{1 , 5} , positions.get(1));
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
