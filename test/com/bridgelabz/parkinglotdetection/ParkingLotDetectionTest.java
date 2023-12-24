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
import java.util.Map;
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
    private User createNormalUser(String userId, String carId, String brand, String color, CarType carType) {
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
    private User createDisabilityUser(String userId, UserType userType, String carId, String brand, String color, CarType carType) {
        Car car = new Car(carId, brand, color, carType);
        return new User(userId, car, userType);
    }
    @Test
    public void A_UserCanParkHisCar() {
        User user = createNormalUser("Mahidhar" , "car1", "BMW", "white", LARGECAR);
        assertParkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void B_UserCanUnParkHisCar() {
        User user = createNormalUser("Mahidhar" , "car1", "BMW", "white", SMALLCAR);
        assertUnparkCar(user, "Mahidhar", "car1");
    }

    @Test
    public void C_UserCanUnParkHisCarByGivingPositionOfCar() {
        User user = createNormalUser("Mahidhar" , "car1", "BMW", "white", SMALLCAR);
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
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        parkingAttendant.parkUserCar(user);
        assertTrue(CarParking.isMyCarParkedInParkingLot("Mahidhar1" , "car1"));
    }

    @Test
    public void H_UserNeedToFindThePositionOfTheCar(){
        CarParking carParking = new CarParking(3 , 1);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        int[] positions = CarParking.getMyCarParkingPosition("Mahidhar1" , "car1");
        assertArrayEquals(new int[]{2 , 0} , positions);
    }

    @Test
    public void I_OwnerShouldGetTheParkingTimeOfTheCar(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
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
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 0);
        user = createNormalUser("Mahidhar3" , "car3", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCarEvenly(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(2 , 0);
        assertTrue(user.equals(user1));
    }


    @Test
    public void K_HandicapCarShouldParkAtNearestPositionEvenItIsLargeCar(){
        CarParking carParking = new CarParking(3);
        User user = createDisabilityUser("Mahidhar2" ,HANDICAP, "car2", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(0 , 0);
        assertTrue(user.equals(user1));
    }


    @Test
    public void L_ParkingAttendantShouldParkCarsWhereMoreSpaceIsAvailable(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        user = createNormalUser("Mahidhar5" , "car5", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        user = createNormalUser("Mahidhar3" , "car3", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserLargeCarAtMoreSpaceAvailable(user);
        User user1 = CarParking.getCarUserDetailsParkedAt(1 , 6);
        System.out.println(Arrays.toString(CarParking.getMyCarParkingPosition("Mahidhar3", "car3")));
        assertTrue(user.equals(user1));
    }


    @Test
    public void M_GetAllThePositionsOfTheSpecificColorCarDetails(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "black", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        user = createNormalUser("Mahidhar5" , "car5", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        user = createNormalUser("Mahidhar3" , "car3", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        ArrayList<User> allWhiteColoredUserCars = policeOfficer.getAllSpecificColorCarUsers(CarParking.multipleParkingLots , "white");
        System.out.println(allWhiteColoredUserCars);
        ArrayList<int[]> positions = policeOfficer.getAllPositionsOfUsers(allWhiteColoredUserCars);
//        for(int[]a : positions) {
//            System.out.println(Arrays.toString(a));
//        }
        assertArrayEquals(new int[]{1 , 5} , positions.get(1));
    }

    @Test
    public void N_GetAllCarsByPassingCarNameAndColorAsArguments(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "Toyata", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "blue", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        user = createNormalUser("Mahidhar5" , "car5", "Toyata", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        user = createNormalUser("Mahidhar3" , "car3", "Toyata", "blue", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        Map<int[] , User> filteredUsersByPositions = policeOfficer.getAllUserCarsByCompanyAndColor(CarParking.multipleParkingLots , "Toyata" , "blue");
        assertTrue(filteredUsersByPositions.containsValue(user));
    }

    @Test
    public void O_GetAllCarsByPassingCarCompany(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "Toyata", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "blue", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        user = createNormalUser("Mahidhar5" , "car5", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        user = createNormalUser("Mahidhar3" , "car3", "Toyata", "blue", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        ArrayList<User>BMWUsers = policeOfficer.getAllSpecificCarCompanyFromParkingLots(CarParking.multipleParkingLots , "BMW");
        assertEquals(2 , BMWUsers.size());
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
