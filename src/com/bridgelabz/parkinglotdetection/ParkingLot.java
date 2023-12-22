package com.bridgelabz.parkinglotdetection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
/*
 * @desc : Represents a Parking Lot with a size and a list of users who parked cars.
 */
public class ParkingLot {
    private int size; // @desc Size of the parking lot
    private ArrayList<User> carUsers; // @desc List of users who parked cars in the parking lot

    /*
     * @desc : Default constructor for ParkingLot class initializing the size to 100.
     * @params : no params
     * @return : void
     */
    public ParkingLot() {
        this.size = 100;
        carUsers = new ArrayList<>(size );
        for (int i = 0; i < size; i++) {
            carUsers.add(null);
        }
    }

    /*
     * @desc : Parameterized constructor for ParkingLot class setting the size.
     * @param size of the parking lot
     * @return : void
     */
    public ParkingLot(int size) {
        System.out.println("parking lot : " + size);
        this.size = size;
        carUsers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            carUsers.add(null);
        }
    }

    /*
     * @desc : Retrieves the size of the parking lot.
     * @params : no params
     * @return The size of the parking lot
     */
    public int getSize() {
        return size;
    }

    /*
     * @desc : Sets the size of the parking lot.
     * @param size The new size for the parking lot
     * @return : void
     */
    public void setSize(int size) {
        this.size = size;
    }

    /*
     * @desc : Retrieves the list of users who parked cars in the parking lot.
     * @params : no params
     * @return The list of users who parked cars
     */
    public ArrayList<User> getCarUsers() {
        return carUsers;
    }

    /*
     * @desc : Sets the list of users who parked cars in the parking lot.
     * @param carUsers The new list of users who parked cars
     * @return : void
     */
    public void setCarUsers(ArrayList<User> carUsers) {
        this.carUsers = carUsers;
    }

    /*
     * @desc : Provides a string representation of the ParkingLot object.
     * @params : no params
     * @return The string representation of the ParkingLot
     */
    @Override
    public String toString() {
        return "ParkingLot{" +
                "size=" + size +
                ", carUsers=" + carUsers +
                '}';
    }

    /*
     * @desc : Overrides equals method to check equality between ParkingLot objects.
     * @param o The object to compare with
     * @return True if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingLot that = (ParkingLot) o;
        return size == that.size && Objects.equals(carUsers, that.carUsers);
    }

    /*
     * @desc : Generates the hash code for ParkingLot objects.
     * @params : no params
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(size, carUsers);
    }

    /*
    @desc : park car at a given position
    @params : position - integer
    @params : user - User
    @return : void
     */
    public void parkCar(int columnIndex, User user) {

        carUsers.set(columnIndex, user);
        user.getCar().setCarParked(true);
        user.getCar().setParkingTime(LocalDateTime.now());
    }
    /*
       @desc : un park car at a given position
       @params : position - integer
       @params : user - User
       @return : void
        */
    public User unParkMyCarByPosition(int columnIndex) {

        User user = carUsers.get(columnIndex);
        carUsers.remove(columnIndex);
        user.getCar().setCarParked(false);
        user.getCar().setUnParkingTime(LocalDateTime.now());
        return user;
    }
    /*
     * @desc: Checks if the parking lot is full based on the number of occupied spaces.
     * @params : no params
     * @return True if the parking lot is full, otherwise false
     */
    public boolean isFull() {
        long count = carUsers.stream()
                .filter(Objects::nonNull)
                .count();
        return count == size;
    }
}
