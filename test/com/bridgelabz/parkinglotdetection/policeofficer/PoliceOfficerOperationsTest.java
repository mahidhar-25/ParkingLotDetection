package com.bridgelabz.parkinglotdetection.policeofficer;

import com.bridgelabz.parkinglotdetection.ParkingLotDetectionTest;
import com.bridgelabz.parkinglotdetection.parkingspace.CarParking;
import com.bridgelabz.parkinglotdetection.parkingattendant.ParkingAttendant;
import com.bridgelabz.parkinglotdetection.police.PoliceOfficer;
import com.bridgelabz.parkinglotdetection.user.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static com.bridgelabz.parkinglotdetection.enums.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.enums.CarType.SMALLCAR;
import static com.bridgelabz.parkinglotdetection.enums.UserType.HANDICAP;
import static org.junit.Assert.*;

public class PoliceOfficerOperationsTest extends ParkingLotDetectionTest{

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

    @Test
    public void P_GetAllTheCarUserWhoParkedMoreThanGivenTime() throws InterruptedException {
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "Toyata", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        Thread.sleep(15000);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "blue", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        Thread.sleep(15000);
        user = createNormalUser("Mahidhar5" , "car5", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        Thread.sleep(15000);
        user = createNormalUser("Mahidhar3" , "car3", "Toyata", "blue", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        Thread.sleep(15000);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        ArrayList<User> getAllCarUser = policeOfficer.getAllCarUserWhoGotParkedMoreThanGivenTime(CarParking.multipleParkingLots , 15);
        assertEquals(3 , getAllCarUser.size());
    }

    @Test
    public void Q_GetAllTheUsersWhoAreDisabledAndHaveASmallCar(){
        CarParking carParking = new CarParking(2);
        User user = createDisabilityUser("Mahidhar1" ,HANDICAP, "car1", "BMW", "black", SMALLCAR);
        user.parkCarAtIndex(0 , 0);
        user = createNormalUser("Mahidhar2" , "car2", "BMW", "white", SMALLCAR);
        user.parkCarAtIndex(0 , 1);
        user = createDisabilityUser("Mahidhar5" ,HANDICAP, "car5", "BMW", "white", LARGECAR);
        user.parkCarAtIndex(1 , 5);
        user = createDisabilityUser("Mahidhar3" ,HANDICAP, "car3", "BMW", "white", SMALLCAR);
        new ParkingAttendant().parkUserCar(user);
        PoliceOfficer policeOfficer =  new PoliceOfficer();
        ArrayList<User> getAllHandiCappedUsers = policeOfficer.getAllHandiCappedUsers(CarParking.multipleParkingLots , HANDICAP);
        System.out.println(getAllHandiCappedUsers);
        ArrayList<User> getAllSmallCarUsers = policeOfficer.getAllGivenCarTypeUsersFromGivenUsers(getAllHandiCappedUsers , SMALLCAR);
        System.out.println(getAllSmallCarUsers);
        Map<int[] , User> filteredUsersByPositions = policeOfficer.getAllUsersCarParkingPositions(getAllSmallCarUsers);
        assertTrue(filteredUsersByPositions.containsValue(user));
    }

}
