package com.bridgelabz.parkinglotdetection;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
/*
 * @desc : Represents a Car Parking system with multiple parking lots.
 */
public class CarParking {
    public static int noOfParkingLots = 2; // @desc Number of parking lots in the system
    public static ArrayList<ParkingLot> multipleParkingLots = new ArrayList<>(2); // @desc List of parking lots

    static {
        for (int i = 0; i < noOfParkingLots; i++) {
            multipleParkingLots.add(new ParkingLot());
        }
    }

    /*
     * @desc : Default constructor for CarParking class initializing parking lots.
     * @params : no params
     * @return : void
     */
    public CarParking() {
        multipleParkingLots = new ArrayList<>();
    }

    /*
     * @desc : Parameterized constructor for CarParking class setting the number of parking lots.
     * @param size Number of parking lots
     * @return : void
     */
    public CarParking(int size) {
        if (size > noOfParkingLots) {
            noOfParkingLots = size;
        }
        for (int i = 0; i < noOfParkingLots; i++) {
            multipleParkingLots.add(new ParkingLot());
        }
    }

    /*
     * @desc : Parameterized constructor for CarParking class setting the number of parking lots and lot size.
     * @param size Number of parking lots
     * @param lotSize Size of each parking lot
     * @return : void
     */
    public CarParking(int size, int lotSize) {
        if (size > noOfParkingLots) {
            noOfParkingLots = size;
        }
        for (int i = 0; i < noOfParkingLots; i++) {
            multipleParkingLots.add(new ParkingLot(lotSize));
        }
    }

    /*
     * @desc : Adds a parking lot to the system with the specified size.
     * @param size of the parking lot to be added
     * @return : void
     */
    public static void addParkingLot(int size) {
        multipleParkingLots.add(new ParkingLot(size));
    }

    /*
     * @desc : Adds a parking lot to the system with a default size.
     * @params : no params
     * @return : void
     */
    public static void addParkingLot() {
        multipleParkingLots.add(new ParkingLot());
    }

    /*
     * @desc : Retrieves the parking positions in the form of a matrix indicating occupied spots.
     * @params : no params
     * @return 2D matrix representing parking positions
     */
    public static ArrayList<ArrayList<Boolean>> getCarParkingPositions() {
        return multipleParkingLots.stream()
                .map(parkingLot -> parkingLot.getCarUsers().stream()
                        .map(Objects::isNull).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

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

    /*
     * @desc : Checks if a car with the given user ID and car ID is parked in any of the parking lots.
     * @param userId User ID
     * @param carId Car ID
     * @return True if the car is parked, false otherwise
     */
    public static boolean isMyCarParkedInParkingLot(String userId, String carId) {
        return multipleParkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getCarUsers().stream())
                .anyMatch(user ->
                        user != null &&
                                userId.equals(user.getUsername()) &&
                                user.getCar() != null &&
                                carId.equals(user.getCar().getCarNo()));
    }
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
    public static void unParkMyCarByPosition(int rowIndex, int columnIndex) {
        multipleParkingLots.get(rowIndex).unParkMyCarByPosition(columnIndex);
    }
}
