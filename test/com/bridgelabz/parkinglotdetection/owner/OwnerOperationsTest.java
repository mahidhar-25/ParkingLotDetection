package com.bridgelabz.parkinglotdetection.owner;

import com.bridgelabz.parkinglotdetection.ParkingLotDetectionTest;
import com.bridgelabz.parkinglotdetection.parkingspace.CarParking;
import com.bridgelabz.parkinglotdetection.parkingspace.operations.GetParkingCarDetails;
import com.bridgelabz.parkinglotdetection.parkingspace.operations.UnParkCar;
import com.bridgelabz.parkinglotdetection.parkingattendant.ParkingAttendant;
import com.bridgelabz.parkinglotdetection.user.User;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.bridgelabz.parkinglotdetection.enums.CarType.LARGECAR;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OwnerOperationsTest extends ParkingLotDetectionTest {

    @Test
    public void D_OwnerShouldKnowParkingLotIsFullOrNot(){
        int indexOfParkingLot = 6;
        assertFalse(GetParkingCarDetails.isParkingLotFull(indexOfParkingLot));
    }


    @Test
    public void E_AirportAuthorityShouldKnow_WhenCompleteParkingLotIsFull(){
        assertFalse(GetParkingCarDetails.isFull());
        fillTheParkingLotWithCars();
        assertTrue(GetParkingCarDetails.isFull());
    }


    @Test
    public void F_ParkingOwnerShouldKnowWhenThereIsAnSpaceInParkingLot(){
        fillTheParkingLotWithCars();
        assertTrue(GetParkingCarDetails.isFull());
        UnParkCar.removeMyCarFromParkingLot("Mahidhar1", "car1");
        assertFalse(GetParkingCarDetails.isFull());
    }



    @Test
    public void I_OwnerShouldGetTheParkingTimeOfTheCar(){
        CarParking carParking = new CarParking(2);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        int[] positions = GetParkingCarDetails.getMyCarParkingPosition("Mahidhar1" , "car1");
        user = GetParkingCarDetails.getCarUserDetailsParkedAt(positions[0], positions[1]);
        LocalDateTime parkingTimeOfMyCar = user.getCar().getParkingTime();
        Duration timeDifference = Duration.between(expectedParkingTime, parkingTimeOfMyCar);
        long maxAllowedDifferenceInSeconds = 60;
        assertTrue(timeDifference.getSeconds() <= maxAllowedDifferenceInSeconds);
    }

}
