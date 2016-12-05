package com.brainacad.azarenko.airport.actions;


/**
* Interface <b>ManagementSystem</b> describes all available actions that are required by specifications of the project.
* @author Lyudmila Azarenko
* @version 1.1
* @see com.brainacad.azarenko.airport.actions.Admin
 * @see com.brainacad.azarenko.airport.actions.User
* */
interface ManagementSystem {
    void createNewArrivalInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance);
    void createNewDepartureInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance);
    void printArrivalInfo();
    void printDepartureInfo();
    void createListPassengers(String flightNumber, String firstName, String lastName, String sex, String nationality, String dateOfBirthday, String passport, String classOfTrip);
    void searchPassengersByFlight(String flightNumber);
    void searchPassengersByName(String firstName);
    void searchPassengersBySurname(String lastName);
    void searchPassengersByPassport(String passport);
    void searchFlightsByCity(String city);
    void searchFlightsByNumber(String flightNumber);
    void searchFlightsByPrice(double price);
    void updatePassengersInfo(String field, String value);
    void updateArrivalsInfo(String field, String value);
    void updateDeparturesInfo(String field, String value);
    void deletePassengers(String field, String value);
    void deleteArrivals(String field, String value);
    void deleteDepartures(String field, String value);
    void printPassengersList(String flightNumber);

}
