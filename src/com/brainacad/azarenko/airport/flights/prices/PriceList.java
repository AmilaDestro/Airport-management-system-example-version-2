package com.brainacad.azarenko.airport.flights.prices;

/**
 * Class <b>PriceList</b> contains method that calculates price for each new flight.
 * @author Lyudmila Azarenko
 * @version 1.1
 * @see com.brainacad.azarenko.airport.flights.FlightDirection*/

public class PriceList {
    private static Double price;


    /**
     * Calculates price for BUSINESS and ECONOMY classes. Final price also depends on a flight distance.
     * @param classOfTrip - indicates passenger's class (ECONOMY or BUSINESS)
     * @param flightDistance - indicates flight distance in kilometers
     * @return Double*/
    public static Double calculatePrice(String classOfTrip, double flightDistance) {
        switch (classOfTrip){
            case "BUSINESS": price = flightDistance*4.2;
                break;
            case "ECONOMY": price = flightDistance*1.63;
                break;
        }
       return price;
    }


}
