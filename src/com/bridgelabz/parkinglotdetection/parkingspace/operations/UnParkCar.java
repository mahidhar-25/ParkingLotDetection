package com.bridgelabz.parkinglotdetection.parkingspace.operations;

import com.bridgelabz.parkinglotdetection.user.User;

import static com.bridgelabz.parkinglotdetection.parkingspace.CarParking.multipleParkingLots;

public class UnParkCar {
    /*
     * @desc : Checks if a car with the given user ID and car ID is parked in any of the parking lots and removes it.
     * @param userId User ID
     * @param carId Car ID
     * @return True if the car is parked, false otherwise
     */
    public static void removeMyCarFromParkingLot(String userId, String carId) {
        multipleParkingLots.forEach(parkingLot ->
                parkingLot.getCarUsers().removeIf(user ->
                        user != null &&
                                user.getUsername().equals(userId) &&
                                user.getCar() != null &&
                                user.getCar().getCarNo().equals(carId)
                )
        );
    }
    /*
     * @desc : un Parks a car at the specified index in the parking lot.
     * @param i Index of the parking lot
     * @param columnIndex Index within the parking lot
     * @param user representing the parked car
     * @return : void
     */
    public static User unParkMyCarByPosition(int rowIndex, int columnIndex) {
        return multipleParkingLots.get(rowIndex).unParkMyCarByPosition(columnIndex);
    }
}
