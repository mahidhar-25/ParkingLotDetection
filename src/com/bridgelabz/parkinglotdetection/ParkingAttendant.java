package com.bridgelabz.parkinglotdetection;

import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static java.lang.Math.max;

public class ParkingAttendant {

    /*
    @desc : parks user car based on type of car
    @params : user as parameter
    @return : void
     */
    public void parkUserCar(User user) {
        

        if(user.getUserType() == UserType.HANDICAP){
            parkCarAtBegin(user);
        }
        Car car = user.getCar();
        if(car.getCarType() == CarType.LARGECAR){
            parkLargeCarAtEnd(user);
        }else{
            parkCarAtBegin(user);
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
    private void parkCarAtBegin(User user) {
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

    /*
    @desc : this function finds the positions of the car where user can park and should be evenly distributed
    @params : user
    @return : void
     */
    public void parkUserCarEvenly(User user) {
        ArrayList<ArrayList<Boolean>> carParkingPositions = CarParking.getCarParkingPositions();

        int maxSize = 0;
        for (ArrayList<Boolean> carParkingPosition : carParkingPositions) {
            maxSize = max(maxSize, carParkingPosition.size());
        }
        int j=0;
        while(j < maxSize){
            for(int i=0;i<carParkingPositions.size();i++){
                if(j< carParkingPositions.get(i).size() && CarParking.multipleParkingLots.get(i).getCarUsers().get(j) == null){
                    CarParking.parkCarAtIndex(i , j , user);
                    return;
                }
            }
            j++;
        }
    }
}
