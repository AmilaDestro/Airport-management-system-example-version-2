package com.brainacad.azarenko.airport.passengers;

/**
 * Class <b>PassengersList</b> contains detailed information about passengers.
 * All parameters for PassengersList object creation are taken by the constructor.
 * @author Lyudmila Azarenko
 * @version 1.1*/

public class PassengersList {
    private String firstName;
    private String lastName;
    private ClassOfTrip classOfTrip;
    private Sex sex;
    private String nationality;
    private String passport;
    private String dateOfBirthday;
    private String flight;

    public PassengersList(String flight, String firstName, String lastName, String sex, String nationality, String dateOfBirthday, String passport, String classOfTrip){
        this.flight = flight;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = Sex.valueOf(sex);
        this.nationality = nationality;
        this.dateOfBirthday = dateOfBirthday;
        this.passport = passport;
        this.classOfTrip = ClassOfTrip.valueOf(classOfTrip);
    }

    public String getFlight() {
        return flight;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDateOfBirthday() {
        return dateOfBirthday;
    }

    public String getPassport() {
        return passport;
    }

    public ClassOfTrip getClassOfTrip() {
        return classOfTrip;
    }

    public Sex getSex() {
        return sex;
    }


}
