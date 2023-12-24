package com.bridgelabz.parkinglotdetection;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

import static com.bridgelabz.parkinglotdetection.UserType.HANDICAP;
import static com.bridgelabz.parkinglotdetection.UserType.NORMALPERSON;

/*
 * @desc : Represents a User with a username and associated car information.
 */
public class User {

    private String username; // @desc User's username
    private Car car; // @desc User's associated car
    private UserType userType;



    /*
     * @desc : Default constructor for User class.
     * @params : no params
     * @return : void
     */
    public User() {
    }

    /*
     * @desc : Parameterized constructor for User class.
     * @param username of the user
     * @param car User's associated car
     * @return : void
     */
    public User(String username, Car car) {
        this.username = username;
        this.car = car;
        this.userType = NORMALPERSON;
    }

    public User(String username, Car car, UserType userType) {
        this(username , car);
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    /*
     * @desc : Retrieves the user's username.
     * @params : no params
     * @return The user's username
     */
    public String getUsername() {
        return username;
    }

    /*
     * @desc : Sets the user's username.
     * @param username The new username for the user
     * @return : void
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /*
     * @desc : Retrieves the user's associated car.
     * @params : no params
     * @return The user's associated car
     */
    public Car getCar() {
        return car;
    }

    /*
     * @desc : Sets the user's associated car.
     * @param car The new car associated with the user
     * @return : void
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /*
     * @desc : Provides a string representation of the User object.
     * @params : no params
     * @return The string representation of the User
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", car=" + car +
                "}\n";
    }

    /*
     * @desc : Overrides equals method to check equality between User objects.
     * @param o The object to compare with
     * @return True if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(car, user.car);
    }

    /*
     * @desc : Generates the hash code for User objects.
     * @params : no params
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, car);
    }


    /*
     * @desc : Parks the user's car at the specified index in the parking lot.
     * @param i The row index in the parking lot
     * @param columnIndex The column index in the parking lot
     * @return : void
     */
    public void parkCarAtIndex(int i, int columnIndex) {
        CarParking.parkCarAtIndex(i, columnIndex, this);
    }

    /*
     * @desc : Unparks the user's car from the parking lot.
     * Removes the car from the parking lot based on the user's information.
     * @params : no params
     * @return : void
     */
    public void unParkMyCar() {
        CarParking.removeMyCarFromParkingLot(username, getCar().getCarNo());
    }

    /*
     * @desc : Unparks the user's car from the parking lot using specified row and column indices.
     * @param rowIndex The row index in the parking lot
     * @param columnIndex The column index in the parking lot
     * @return The user object after unparking the car
     */
    public User unParkMyCar(int rowIndex, int columnIndex) {
        return CarParking.unParkMyCarByPosition(rowIndex, columnIndex);
    }

}
