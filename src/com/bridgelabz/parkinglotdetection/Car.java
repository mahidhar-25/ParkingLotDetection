package com.bridgelabz.parkinglotdetection;

import java.util.Objects;

/*
 * @desc : Represents a Car with its attributes.
 */
public class Car {
    private String carNo; // @desc Car's license plate number
    private String company; // @desc Car's manufacturing company
    private String color; // @desc Car's color
    private CarType carType; // @desc Type of the car (e.g., sedan, SUV)

    /*
     * @desc : Default constructor for Car class.
     * @params : no params
     * @return : void
     */
    public Car() {
    }

    /*
     * @desc : Parameterized constructor for Car class.
     * @param carNo License plate number of the car
     * @param company Manufacturing company of the car
     * @param color of the car
     * @param carType Type of the car
     * @return : void
     */
    public Car(String carNo, String company, String color, CarType carType) {
        this.carNo = carNo;
        this.company = company;
        this.color = color;
        this.carType = carType;
    }

    /*
     * @desc : Retrieves the car's license plate number.
     * @params : no params
     * @return The license plate number
     */
    public String getCarNo() {
        return carNo;
    }

    /*
     * @desc : Sets the car's license plate number.
     * @param carNo The new license plate number
     * @return : void
     */
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    /*
     * @desc : Retrieves the car's manufacturing company.
     * @params : no params
     * @return The manufacturing company
     */
    public String getCompany() {
        return company;
    }

    /*
     * @desc : Sets the car's manufacturing company.
     * @param company The new manufacturing company
     * @return : void
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /*
     * @desc : Retrieves the car's color.
     * @params : no params
     * @return The car's color
     */
    public String getColor() {
        return color;
    }

    /*
     * @desc : Sets the car's color.
     * @param color The new color of the car
     * @return : void
     */
    public void setColor(String color) {
        this.color = color;
    }

    /*
     * @desc : Retrieves the type of the car.
     * @params : no params
     * @return The type of the car
     */
    public CarType getCarType() {
        return carType;
    }

    /*
     * @desc : Sets the type of the car.
     * @param carType The new type of the car
     * @return : void
     */
    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    /*
     * @desc : Overrides equals method to check equality between Car objects.
     * @param o The object to compare with
     * @return True if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carNo, car.carNo) && Objects.equals(company, car.company) && Objects.equals(color, car.color) && carType == car.carType;
    }

    /*
     * @desc : Generates the hash code for Car objects.
     * @params : no params
     * @return The hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(carNo, company, color, carType);
    }

    /*
     * @desc : Provides a string representation of Car object.
     * @params : no params
     * @return The string representation of the Car
     */
    @Override
    public String toString() {
        return "Car{" +
                "carNo='" + carNo + '\'' +
                ", company='" + company + '\'' +
                ", color='" + color + '\'' +
                ", carType=" + carType +
                '}';
    }
}
