package com.brainacad.azarenko.airport.actions;


/**
 * Class <b>User</b> describes actions available for users of the application (not administrators).
 * Some of them copy implementation from <b>Admin</b> class. The rest throws UnsupportedOperationException.
 * @see com.brainacad.azarenko.airport.actions.ManagementSystem
 * @see com.brainacad.azarenko.airport.actions.Admin*/

public class User implements ManagementSystem {

    /**
     * Allows user or administrator to view information about all arrivals.*/
    public void printArrivalInfo() {
        new Admin().printArrivalInfo();
    }

    /**
     * Allows user or administrator to view information about all departures.*/
    public void printDepartureInfo() {
        new Admin().printDepartureInfo();
    }


    /**
     * Creates a new record about arrival in the database. Unavailable for users.*/
    public void createNewArrivalInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance) {
        throw new UnsupportedOperationException();
    }


    /**
     * Creates a new record about departure in the database. Unavailable for users.*/
    public void createNewDepartureInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance) {
        throw new UnsupportedOperationException();
    }


    /**
     * Creates new records about passengers. Before that makes sure that the passenger is registered on an existing flight number.
     * Unavailable for users.*/
    public void createListPassengers(String flightNumber, String firstName, String lastName, String sex, String nationality, String dateOfBirthday, String passport, String classOfTrip) {
        throw new UnsupportedOperationException();
    }


    /**
     * Searches passenger by the specified flight number. Flight number is mentioned as method parameter.
     * Unavailable for users.*/
    public void searchPassengersByFlight(String flightNumber) {
        throw new UnsupportedOperationException();
    }


    /**
     * Searches passenger by the first name. First name is mentioned as method parameter.
     * Unavailable for users.*/
    public void searchPassengersByName(String firstName) {
        throw new UnsupportedOperationException();
    }


    /**
     * Searches passenger by the last name. Last name is mentioned as method parameter.
     * Unavailable for users.*/
    public void searchPassengersBySurname(String lastName) {
        throw new UnsupportedOperationException();
    }


    /**
     * Searches passenger by the passport ID. Passport ID is mentioned as method parameter.
     * Unavailable for users.*/
    public void searchPassengersByPassport(String passport) {
        throw new UnsupportedOperationException();
    }


    /**
     * Searches flights (arrivals and departures) by the city. City is mentioned as method parameter.*/
    public void searchFlightsByCity(String city) {
        new Admin().searchFlightsByCity(city);
    }


    /**
     * Searches flights (arrivals and departures) by the price. Price is mentioned as method parameter.
     * User or administrator should know the exact price in order to use this method effectively.*/
    public void searchFlightsByPrice(double price) {
        new Admin().searchFlightsByPrice(price);
    }


    /**
     * Searches flights (arrivals and departures) by the flight number. Flight number is mentioned as method parameter.*/
    public void searchFlightsByNumber(String flightNumber) {
        new Admin().searchFlightsByNumber(flightNumber);
    }


    /**
     * Allows to update or delete records in Passengers table of the database.
     * Unavailable for users.*/
    public void updatePassengersInfo(String field, String value) {
        throw new UnsupportedOperationException();
    }


    /**
     * Allows to update or delete records in Arrival table of the database.
     * Unavailable for users.*/
    public void updateArrivalsInfo(String field, String value) {
        throw new UnsupportedOperationException();
    }


    /**
     * Allows to update or delete records in Departure table of the database.
     * Unavailable for users.*/
    public void updateDeparturesInfo(String field, String value) {
        throw new UnsupportedOperationException();
    }


    /**
     * Allows administrator to view passenger list of the specified flight number. Flight number is method parameter.
     * Unavailable for users.*/
    public void printPassengersList(String flightNumber){
        throw new UnsupportedOperationException();
    }

    public void deletePassengers(String field, String value){
        throw new UnsupportedOperationException();
    }

    public void deleteArrivals(String field, String value){
        throw new UnsupportedOperationException();
    }

    public void deleteDepartures(String field, String value){
        throw new UnsupportedOperationException();
    }
}