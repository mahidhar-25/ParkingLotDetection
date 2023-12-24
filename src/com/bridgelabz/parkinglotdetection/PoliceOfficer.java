package com.bridgelabz.parkinglotdetection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PoliceOfficer {
    public PoliceOfficer() {
    }

    /*
     * @desc Retrieves all users with cars of a specific color across all parking lots.
     * @param carColor The color of the cars to filter by
     * @return List of users having cars with the specified color
     */
    public ArrayList<User> getAllSpecificColorCarUsersFromParkingLots(ArrayList<ParkingLot> allParkingLots , String carColor) {
        return allParkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getCarUsers().stream())
                .filter(user -> user != null && user.getCar().getColor().equalsIgnoreCase(carColor))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /*
     * @desc Retrieves all users with cars of a specific color across all parking lots.
     * @param carColor The color of the cars to filter by
     * @return List of users having cars with the specified color
     */
    public ArrayList<User> getAllSpecificColorCarUsers( ArrayList<ParkingLot> allParkingLots , String carColor) {
        return allParkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getCarUsers().stream())
                .filter(user -> user != null && user.getCar().getColor().equalsIgnoreCase(carColor))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /*
     * @desc Retrieves all users with cars of a specific company across all given Users .
     * @param String carCompany The color of the cars to filter by
     * @param ArrayList<User> allParkingLots
     * @return List of users having cars with the specified color
     */
    public ArrayList<User> getAllSpecificCarCompanyFromParkingLots(ArrayList<ParkingLot> allParkingLots, String carCompany) {
        return allParkingLots.stream()
                .flatMap(parkingLot -> parkingLot.getCarUsers().stream())
                .filter(user -> user != null && user.getCar().getCompany().equalsIgnoreCase(carCompany))
                .collect(Collectors.toCollection(ArrayList::new));
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
    /*
     * @desc Retrieves the positions of users' cars provided in the input list and fil;tered the by color and company.
     * @param String CarCompany , String carColor
     * @return Map<int[] , User>
     */
    public Map<int[], User> getAllUserCarsByCompanyAndColor(ArrayList<ParkingLot> allParkingLots , String carCompany, String carColor) {
        Map<int[], User> requiredCarsByPosition = new LinkedHashMap<>();
       ArrayList<User>allSpecificColorCars = getAllSpecificColorCarUsers(allParkingLots , carColor);
       ArrayList<User> filteredCarUsers = getAllSpecificCarCompany(allSpecificColorCars , carCompany);
       ArrayList<int[]>positions = getAllPositionsOfUsers(filteredCarUsers);
       for(int i=0;i< positions.size();i++){
           requiredCarsByPosition.put(positions.get(i),filteredCarUsers.get(i) );
       }
       return requiredCarsByPosition;
    }
    /*
     * @desc Retrieves all users with cars of a specific company across all given Users .
     * @param String carCompany The color of the cars to filter by
     * @param ArrayList<User> allParkingLots
     * @return List of users having cars with the specified color
     */
    public ArrayList<User> getAllSpecificCarCompany(ArrayList<User> allParkingLots, String carCompany) {
        return allParkingLots.stream()
                .filter(user -> user != null && user.getCar().getCompany().equalsIgnoreCase(carCompany))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /*
        @desc Retrieves users whose cars have been parked for longer than the provided duration.
        @param multipleParkingLots A list of parking lots to search for users.
        @param providedTime The duration threshold in seconds to filter parked users.
        @return An ArrayList of users whose cars have been parked for more than the provided duration.
    */
    public ArrayList<User> getAllCarUserWhoGotParkedMoreThanGivenTime(ArrayList<ParkingLot> multipleParkingLots, int providedTime) {
        ArrayList<User> filteredUser = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now();

        for (ParkingLot parkingLot : multipleParkingLots) {
            for (User user : parkingLot.getCarUsers()) {
                if (user != null) {
                    LocalDateTime parkingTime = user.getCar().getParkingTime();
                    Duration duration = Duration.between(parkingTime, currentTime);
                    if (duration.toSeconds() > providedTime) {
                        filteredUser.add(user);
                    }
                }
            }
        }

        return filteredUser;
    }
}

