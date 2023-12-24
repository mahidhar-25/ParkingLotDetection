package com.bridgelabz.parkinglotdetection.parkingspace;

import java.util.ArrayList;

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
        noOfParkingLots = size;
        multipleParkingLots = new ArrayList<>();
        for (int i = 0; i < size; i++) {
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
        multipleParkingLots = new ArrayList<>();
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
}
