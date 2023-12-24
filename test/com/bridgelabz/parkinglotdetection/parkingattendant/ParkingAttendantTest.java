package com.bridgelabz.parkinglotdetection.parkingattendant;

import com.bridgelabz.parkinglotdetection.ParkingLotDetectionTest;
import com.bridgelabz.parkinglotdetection.parkingspace.CarParking;
import com.bridgelabz.parkinglotdetection.parkingspace.operations.GetParkingCarDetails;
import com.bridgelabz.parkinglotdetection.user.User;
import org.junit.Test;

import java.util.Arrays;

import static com.bridgelabz.parkinglotdetection.enums.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.enums.CarType.SMALLCAR;
import static com.bridgelabz.parkinglotdetection.enums.UserType.HANDICAP;
import static org.junit.Assert.assertTrue;

public class ParkingAttendantTest extends ParkingLotDetectionTest {

    @Test
    public void G_ParkingAttendantCanDecideWhereToParkCar(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        ParkingAttendant parkingAttendant = new ParkingAttendant();
        parkingAttendant.parkUserCar(user);
        assertTrue(GetParkingCarDetails.isMyCarParkedInParkingLot("Mahidhar1" , "car1"));
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
        User user1 = GetParkingCarDetails.getCarUserDetailsParkedAt(2 , 0);
        assertTrue(user.equals(user1));
    }


    @Test
    public void K_HandicapCarShouldParkAtNearestPositionEvenItIsLargeCar(){
        CarParking carParking = new CarParking(3);
        User user = createDisabilityUser("Mahidhar2" ,HANDICAP, "car2", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        User user1 = GetParkingCarDetails.getCarUserDetailsParkedAt(0 , 0);
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
        User user1 = GetParkingCarDetails.getCarUserDetailsParkedAt(1 , 6);
        System.out.println(Arrays.toString(GetParkingCarDetails.getMyCarParkingPosition("Mahidhar3", "car3")));
        assertTrue(user.equals(user1));
    }


}
