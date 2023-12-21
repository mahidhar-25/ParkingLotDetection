package com.bridgelabz.parkinglotdetection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static com.bridgelabz.parkinglotdetection.CarType.LARGECAR;
import static org.junit.Assert.assertTrue;

public class ParkingLotDetectionTest {

    /*
    @desc : this test case validates whether an user can park or not , if there is available position to park ,
     park the car

     */

    @Test
    public void UserCanParkHisCar(){
        Car car = new Car("car1" , "BMW" , "white" , LARGECAR);
        User user = new User("Mahidhar" , car);
        ArrayList<ArrayList<Boolean>> CarParkingPositions = CarParking.getCarParkingPositions();
        OptionalInt rowIndex = IntStream.range(0, CarParkingPositions.size())
                .filter(i -> CarParkingPositions.get(i).stream().anyMatch(Boolean::booleanValue))
                .findFirst();

        rowIndex.ifPresent(i -> {
            int columnIndex = CarParkingPositions.get(i).indexOf(true);
            user.parkCarAtIndex(i, columnIndex);
        });
        System.out.println(user);
        System.out.println(CarParking.multipleParkingLots);

        assertTrue(CarParking.isMyCarParkedInParkingLot("Mahidhar" , "car1"));
    }
}
