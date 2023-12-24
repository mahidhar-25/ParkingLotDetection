package com.bridgelabz.parkinglotdetection.parkingspace.operations;

import com.bridgelabz.parkinglotdetection.user.User;

import static com.bridgelabz.parkinglotdetection.parkingspace.CarParking.multipleParkingLots;

public class ParkCar {
    /*
     * @desc : Parks a car at the specified index in the parking lot.
     * @param i Index of the parking lot
     * @param columnIndex Index within the parking lot
     * @param user representing the parked car
     * @return : void
     */
    public static void parkCarAtIndex(int i, int columnIndex, User user) {
        multipleParkingLots.get(i).parkCar(columnIndex, user);
    }
}
