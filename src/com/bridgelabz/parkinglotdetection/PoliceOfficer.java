package com.bridgelabz.parkinglotdetection;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PoliceOfficer {
    public PoliceOfficer() {
    }

    /*
     * @desc Retrieves all users with cars of a specific color across all parking lots.
     * @param carColor The color of the cars to filter by
     * @return List of users having cars with the specified color
     */
    public ArrayList<User> getAllSpecificColorCarUsers(String carColor) {
        ArrayList<ParkingLot> allParkingLots = CarParking.multipleParkingLots;

        return allParkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getCarUsers().stream()) // Get users from each parking lot
                .filter(user -> user != null && user.getCar().getColor().equalsIgnoreCase(carColor)) // Filter by car color
                .collect(Collectors.toCollection(ArrayList::new)); // Collect users into a list
    }

    /*
     * @desc Retrieves the positions of users' cars provided in the input list.
     * @param users List of users whose car positions need to be retrieved
     * @return List of positions for each user's car
     */
    public ArrayList<int[]> getAllPositionsOfUsers(ArrayList<User> users) {
        ArrayList<Integer[]> carPosition = new ArrayList<>();
        return users.stream()
                .map(user -> CarParking.getMyCarParkingPosition(user.getUsername(), user.getCar().getCarNo()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}

