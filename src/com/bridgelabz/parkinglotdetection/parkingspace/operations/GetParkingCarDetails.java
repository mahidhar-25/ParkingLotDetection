package com.bridgelabz.parkinglotdetection.parkingspace.operations;

import com.bridgelabz.parkinglotdetection.parkingspace.ParkingLot;
import com.bridgelabz.parkinglotdetection.user.User;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.bridgelabz.parkinglotdetection.parkingspace.CarParking.multipleParkingLots;

public class GetParkingCarDetails {

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
     * @desc : Checks if the parking lot at the specified index is full.
     * @param indexOfParkingLot The index of the parking lot to check
     * @return True if the parking lot is full, otherwise false
     */
    public static boolean isParkingLotFull(int indexOfParkingLot) {
        try {
            if (indexOfParkingLot >= 0 && indexOfParkingLot < multipleParkingLots.size()) {
                return multipleParkingLots.get(indexOfParkingLot).isFull();
            } else {
                System.out.println("Index is out of bounds. Please provide a valid index.");
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException: " + e.getMessage());
            return false;
        }
    }

    /*
    @desc : checks whether all parking lots re full or not
    @params : no params
    @return : boolean
     */
    public static boolean isFull() {
        return multipleParkingLots.stream().allMatch(ParkingLot::isFull);
    }
    /*
    @desc : get my car parking positions
    @param userId User ID
     @param carId Car ID
     @return : int[] array with car positions

     */
    public static int[]  getMyCarParkingPosition(String userId, String carId) {
        for (int i = 0; i < multipleParkingLots.size(); i++) {
            ArrayList<User> carUsers = multipleParkingLots.get(i).getCarUsers();
            for (int j = 0; j < carUsers.size(); j++) {
                User user = carUsers.get(j);
//                if(user!=null){
//                    System.out.println(user);
//                }
                if (user!=null && user.getUsername().equals(userId) && user.getCar().getCarNo().equals(carId)) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; // Indicate car not found
    }


    public static User getCarUserDetailsParkedAt(int rowIndex, int columnIndex) {
        return multipleParkingLots.get(rowIndex).getCarUsers().get(columnIndex);
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


}
