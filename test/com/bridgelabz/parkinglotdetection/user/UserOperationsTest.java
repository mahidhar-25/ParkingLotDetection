package com.bridgelabz.parkinglotdetection.user;

import com.bridgelabz.parkinglotdetection.ParkingLotDetectionTest;
import com.bridgelabz.parkinglotdetection.parkingspace.CarParking;
import com.bridgelabz.parkinglotdetection.parkingspace.operations.GetParkingCarDetails;
import com.bridgelabz.parkinglotdetection.parkingattendant.ParkingAttendant;
import org.junit.Test;

import static com.bridgelabz.parkinglotdetection.enums.CarType.LARGECAR;
import static com.bridgelabz.parkinglotdetection.enums.CarType.SMALLCAR;
import static org.junit.Assert.assertArrayEquals;

public class UserOperationsTest extends ParkingLotDetectionTest {
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
    public void H_UserNeedToFindThePositionOfTheCar(){
        CarParking carParking = new CarParking(3 , 1);
        User user = createNormalUser("Mahidhar1" , "car1", "BMW", "white", LARGECAR);
        new ParkingAttendant().parkUserCar(user);
        int[] positions = GetParkingCarDetails.getMyCarParkingPosition("Mahidhar1" , "car1");
        assertArrayEquals(new int[]{2 , 0} , positions);
    }
}
