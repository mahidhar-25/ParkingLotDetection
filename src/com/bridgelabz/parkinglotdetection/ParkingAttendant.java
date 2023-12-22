package com.bridgelabz.parkinglotdetection;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ParkingAttendant {

    /*
    @desc : parks user car based on type of car
    @params : user as parameter
    @return : void
     */
    public void parkUserCar(User user) {
        Car car = user.getCar();
        if(car.getCarType() == CarType.LARGECAR){
            parkLargeCarAtEnd(user);
        }else{
            parkSmallCarAtBegin(user);
        }
    }

    /*
    @desc : park car at last available positions
    @params : user as parameter
    @return : void
     */
    private void parkLargeCarAtEnd(User user) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();
        OptionalInt rowIndex = findLastRowIndex(carParkingPositions);
        rowIndex.ifPresent(i -> {
            int columnIndex = carParkingPositions.get(i).lastIndexOf(true);
            CarParking.parkCarAtIndex(i, columnIndex , user);
        });
    }
    /*
       @desc : park car at first available positions
       @params : user as parameter
       @return : void
        */
    private void parkSmallCarAtBegin(User user) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();
        OptionalInt rowIndex = findFirstRowIndex(carParkingPositions);
        rowIndex.ifPresent(i -> {
            int columnIndex = carParkingPositions.get(i).indexOf(true);
            CarParking.parkCarAtIndex(i, columnIndex , user);
        });
    }

    /*
     * @desc : Finds the first index of the row where a parking space is available.
     * @param carParkingPositions List of parking positions
     * @return Optional index of the available row
     */
    private OptionalInt findFirstRowIndex(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        return IntStream.range(0, carParkingPositions.size())
                .filter(i -> carParkingPositions.get(i).stream().anyMatch(Boolean::booleanValue))
                .findFirst();
    }

    /*
     * @desc : Finds the last index of the row where a parking space is available.
     * @param carParkingPositions List of parking positions
     * @return Optional index of the available row
     */
    private OptionalInt findLastRowIndex(ArrayList<ArrayList<Boolean>> carParkingPositions) {
        return IntStream.range(0, carParkingPositions.size())
                .filter(i -> carParkingPositions.get(i).stream().anyMatch(Boolean::booleanValue))
                .reduce((first, second) -> second);
    }
}