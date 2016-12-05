package com.brainacad.azarenko.airport.flights;

import com.brainacad.azarenko.airport.flights.prices.PriceList;

/**
 * Class <b>FlightDirection</b> contains detailed information about flights.
 * @author Lyudmila Azarenko
 * @version 1.1*/

public class FlightDirection {

    private String date;
    private String time;
    private String flightNumber;
    private String city;
    private String terminal;
    private FlightStatus flightStatus;
    private Double priceBusiness;
    private Double priceEconomy;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setFlightStatus(String status){
        this.flightStatus = FlightStatus.valueOf(status);
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setPriceBusiness(double flightDistance) {
        this.priceBusiness = PriceList.calculatePrice("BUSINESS", flightDistance);
    }

    public Double getPriceBusiness() {
        return priceBusiness;
    }

    public void setPriceEconomy(double flightDistance) {
        this.priceEconomy = PriceList.calculatePrice("ECONOMY", flightDistance);
    }

    public Double getPriceEconomy() {
        return priceEconomy;
    }


    /**
     * Nested class <b>Arrival</b> contains information only about arrivals.
     * Flight parameters are taken by the constructor.*/
    public class Arrival extends FlightDirection {

        public Arrival(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distanceA){
            this.setDate(date);
            this.setCity(city);
            this.setTime(time);
            this.setFlightNumber(flightNumber);
            this.setFlightStatus(flightStatus);
            this.setTerminal(terminal);
            this.setPriceBusiness(distanceA);
            this.setPriceEconomy(distanceA);
        }
    }


    /**
     * Nested class <b>Departure</b> contains information only about departures.
     * Flight parameters are taken by the constructor.*/
    public class Departure extends FlightDirection {

        public Departure(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distanceD){
            this.setDate(date);
            this.setCity(city);
            this.setTime(time);
            this.setFlightNumber(flightNumber);
            this.setFlightStatus(flightStatus);
            this.setTerminal(terminal);
            this.setPriceBusiness(distanceD);
            this.setPriceEconomy(distanceD);
        }
    }
}
