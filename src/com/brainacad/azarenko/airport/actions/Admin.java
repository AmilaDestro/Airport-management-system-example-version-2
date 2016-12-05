package com.brainacad.azarenko.airport.actions;

import com.brainacad.azarenko.airport.flights.FlightDirection;
import com.brainacad.azarenko.airport.passengers.PassengersList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;


/**
* Class <b>Admin</b> contains the most wide set of actions by implementing all methods of
* <b>ManagementSystem</b> interface without throwing UnsupportedOperationException.
* @author Lyudmila Azarenko
* @version 1.1*
 * @see com.brainacad.azarenko.airport.actions.ManagementSystem
 */

public class Admin implements ManagementSystem {

    /**
     * Checking Set, contains instances of FlightDirection class. Used in methods that implement searching algorithm.
     * @see com.brainacad.azarenko.airport.flights.FlightDirection*/
    private static Set<FlightDirection> flights = new LinkedHashSet<>();

    /**
     * Checking Collection, contains instances of PassengersList class. Used in methods that implement searching algorithm.
     * @see com.brainacad.azarenko.airport.passengers.PassengersList*/
    private static Collection<PassengersList> passengers = new LinkedList<>();

    /**
     * Connection type variable is required for establishing a database connection.*/
    private Connection dbConnection = null;

    /**
     * Variable of primitive type is used for automatic counting of passenger ID*/
    private static int counter = 1;

    /**
     *Another counter variable of primitive type for methods which implement searching algorithm. */
    private static int matches = 0;

    /**
     * Checking collection consists of String one-dimensional array. The array contains records about arrivals.
     * Used to find a parameter of new JTable object.*/
    private Collection<String[]> structuredArrivals = new ArrayList<>();

    /**
     * Checking collection consists of String one-dimensional array. The array contains records about departures.
     * Used to find a parameter of new JTable object.*/
    private Collection<String[]> structuredDepartures = new ArrayList<>();

    /**
     * Checking collection consists of String one-dimensional array. The array contains records about passengers.
     * Used to find a parameter of new JTable object.*/
    private Collection<String[]> sortedByFlightPassengers = new ArrayList<>();



    /**
     * Establishes connection to the database*/
    private void connectToDB() {
        try {
            Class.forName("org.sqlite.core.DB");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection("jdbc:sqlite:\\AirportProject\\src\\com\\brainacad\\azarenko\\airport\\actions\\database\\airport.db");
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }

    }



    /**
     * Creates a new record about arrival in the database*/
    public void createNewArrivalInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance) {
        FlightDirection.Arrival newArrival = null;
        try {
            newArrival = new FlightDirection().new Arrival(date, city, time, flightNumber, flightStatus, terminal, distance);
        }catch (IllegalArgumentException e){
            String message = e.getMessage();
            showIllegalArgumentException(message);
        }
        connectToDB();
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("insert into Arrival values(\'" + flightNumber + "\',\'" + date + "\',\'" + time + "\',\'" + city + "\'," + distance + ",\'" + flightStatus + "\',\'" + terminal + "\'," + newArrival.getPriceBusiness() + "," + newArrival.getPriceEconomy() + ")");
            JFrame jFrameArrival = new JFrame();
            jFrameArrival.setBounds(100,100,250,60);
            jFrameArrival.setTitle("Success");
            jFrameArrival.setResizable(false);
            jFrameArrival.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrameArrival.getContentPane();
            jFrameArrival.setVisible(true);

            JPanel jPanelArrival = new JPanel();
            jPanelArrival.setLayout(new FlowLayout());
            jFrameArrival.add(jPanelArrival);

            JLabel jLabelArrival = new JLabel("New arrival record created!");
            jPanelArrival.add(jLabelArrival);

        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Creates a new record about departure in the database*/
    public void createNewDepartureInfo(String date, String city, String time, String flightNumber, String flightStatus, String terminal, Double distance) {
        FlightDirection.Departure newDeparture = null;
        try {
             newDeparture = new FlightDirection().new Departure(date, city, time, flightNumber, flightStatus, terminal, distance);
        }catch (IllegalArgumentException e){
            String message = e.getMessage();
            showIllegalArgumentException(message);
        }
        connectToDB();
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("insert into Departure values(\'" + flightNumber + "\',\'" + date + "\',\'" + time + "\',\'" + city + "\'," + distance + ",\'" + flightStatus + "\',\'" + terminal + "\'," + newDeparture.getPriceBusiness() + "," + newDeparture.getPriceEconomy() + ")");
            JFrame jFrameDeparture = new JFrame();
            jFrameDeparture.setBounds(100,100,250,60);
            jFrameDeparture.setTitle("Success");
            jFrameDeparture.setResizable(false);
            jFrameDeparture.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrameDeparture.getContentPane();
            jFrameDeparture.setVisible(true);

            JPanel jPanelDeparture = new JPanel();
            jPanelDeparture.setLayout(new FlowLayout());
            jFrameDeparture.add(jPanelDeparture);

            JLabel jLabelDeparture = new JLabel("New departure record created!");
            jPanelDeparture.add(jLabelDeparture);
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Allows user or administrator to view information about all arrivals*/
    public void printArrivalInfo() {
        connectToDB();
        JFrame frame = new JFrame("All arrivals");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("select * from Arrival");
            ResultSet arrivalsTable = statement.getResultSet();
            String[] columnNames = {"Date", "Time", "FlightNumber", "City", "Status", "Terminal", "BusinessPrice", "EconomyPrice"};
            while (arrivalsTable.next()) {
                String flightNumber = arrivalsTable.getString("FlightNumber");
                String date = arrivalsTable.getString("Date");
                String time = arrivalsTable.getString("Time");
                String city = arrivalsTable.getString("City");
                String status = arrivalsTable.getString("FlightStatus");
                String terminal = arrivalsTable.getString("Terminal");
                String business = arrivalsTable.getString("BusinessPrice");
                String economy = arrivalsTable.getString("EconomyPrice");
                String[] oneRow = {date, time, flightNumber, city, status, terminal, business, economy};
                structuredArrivals.add(oneRow);
            }
            String[][] rowsOfTable = structuredArrivals.toArray(new String[structuredArrivals.size()][]);
            JTable table = new JTable(rowsOfTable, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            frame.setPreferredSize(new Dimension(450, 200));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            structuredArrivals.clear();
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Allows user or administrator to view information about all departures*/
    public void printDepartureInfo() {
        connectToDB();
        JFrame frame = new JFrame("All departures");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("select * from Departure");
            ResultSet departuresTable = statement.getResultSet();
            String[] columnNames = {"Date", "Time", "FlightNumber", "City", "Status", "Terminal", "BusinessPrice", "EconomyPrice"};
            while (departuresTable.next()) {
                String flightNumber = departuresTable.getString("FlightNumber");
                String date = departuresTable.getString("Date");
                String time = departuresTable.getString("Time");
                String city = departuresTable.getString("City");
                String status = departuresTable.getString("FlightStatus");
                String terminal = departuresTable.getString("Terminal");
                String business = departuresTable.getString("BusinessPrice");
                String economy = departuresTable.getString("EconomyPrice");
                String[] oneRow = {date, time, flightNumber, city, status, terminal, business, economy};
                structuredDepartures.add(oneRow);
            }
            String[][] rowsOfTable = structuredDepartures.toArray(new String[structuredArrivals.size()][]);
            JTable table = new JTable(rowsOfTable, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.getContentPane().add(scrollPane);
            frame.setPreferredSize(new Dimension(450, 200));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            structuredDepartures.clear();
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**@return int
     * Method calculates passenger ID so that to create a new record in Passengers table of the database.
     * Administrator doesn't fill in a field with this information manually.*/
    private int numberOfPassengers() {
        connectToDB();
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("select count (*) from Passengers");
            ResultSet numberOfRows = statement.getResultSet();
            counter = numberOfRows.getInt(1) + 1;
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
        return counter;
    }



    /**
     * Creates new records about passengers. Before that makes sure that the passenger is registered on an existing flight number.*/
    public void createListPassengers(String flightNumber, String firstName, String lastName, String sex, String nationality, String dateOfBirthday, String passport, String classOfTrip) {
        connectToDB();
        try (Statement statement = dbConnection.createStatement()) {
            statement.execute("select * from Arrival where FlightNumber = \'" + flightNumber + "\'");
            ResultSet searchArrival = statement.getResultSet();
            if (!searchArrival.next()) {
                statement.execute("select * from Departure where FlightNumber = \'" + flightNumber + "\'");
                ResultSet searchDeparture = statement.getResultSet();
                if (!searchDeparture.next()) {
                    JFrame jFrameNoFlights = new JFrame();
                    jFrameNoFlights.setBounds(100,100,250,60);
                    jFrameNoFlights.setTitle("No results");
                    jFrameNoFlights.setResizable(false);
                    jFrameNoFlights.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jFrameNoFlights.getContentPane();
                    jFrameNoFlights.setVisible(true);

                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(new FlowLayout());
                    jFrameNoFlights.add(jPanel);

                    JLabel jLabelNoFlights = new JLabel("Passenger not found");
                    jPanel.add(jLabelNoFlights);
                } else {
                    try {
                        PassengersList addPassenger = new PassengersList(flightNumber, firstName, lastName, sex, nationality, dateOfBirthday, passport, classOfTrip);
                        statement.execute("insert into Passengers values(" + numberOfPassengers() + ",\'" + addPassenger.getFlight() + "\',\'" + addPassenger.getFirstName() + "\',\'" + addPassenger.getLastName() + "\',\'" + addPassenger.getSex() + "\',\'" + addPassenger.getNationality() + "\',\'" + addPassenger.getDateOfBirthday() + "\',\'" + addPassenger.getPassport() + "\',\'" + addPassenger.getClassOfTrip() + "\')");
                        System.out.println("New record created!");
                        JFrame jFramePassenger = new JFrame();
                        jFramePassenger.setBounds(100,100,250,60);
                        jFramePassenger.setTitle("Success");
                        jFramePassenger.setResizable(false);
                        jFramePassenger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        jFramePassenger.getContentPane();
                        jFramePassenger.setVisible(true);

                        JPanel jPanelPassenger = new JPanel();
                        jPanelPassenger.setLayout(new FlowLayout());
                        jFramePassenger.add(jPanelPassenger);

                        JLabel jLabelPassenger = new JLabel("New passenger record created!");
                        jPanelPassenger.add(jLabelPassenger);
                    } catch (IllegalArgumentException e) {
                        String message = e.getMessage();
                        showIllegalArgumentException(message);
                    }
                }
            } else {
                try {
                    PassengersList addPassenger = new PassengersList(flightNumber, firstName, lastName, sex, nationality, dateOfBirthday, passport, classOfTrip);
                    statement.execute("insert into Passengers values(" + numberOfPassengers() + ",\'" + addPassenger.getFlight() + "\',\'" + addPassenger.getFirstName() + "\',\'" + addPassenger.getLastName() + "\',\'" + addPassenger.getSex() + "\',\'" + addPassenger.getNationality() + "\',\'" + addPassenger.getDateOfBirthday() + "\',\'" + addPassenger.getPassport() + "\',\'" + addPassenger.getClassOfTrip() + "\')");
                    JFrame jFramePassenger = new JFrame();
                    jFramePassenger.setBounds(100,100,250,60);
                    jFramePassenger.setTitle("Success");
                    jFramePassenger.setResizable(false);
                    jFramePassenger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    jFramePassenger.getContentPane();
                    jFramePassenger.setVisible(true);

                    JPanel jPanelPassenger = new JPanel();
                    jPanelPassenger.setLayout(new FlowLayout());
                    jFramePassenger.add(jPanelPassenger);

                    JLabel jLabelPassenger = new JLabel("New passenger record created!");
                    jPanelPassenger.add(jLabelPassenger);
                } catch (IllegalArgumentException e) {
                    String message = e.getMessage();
                    showIllegalArgumentException(message);
                }
            }
        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Searches passenger by the specified flight number. Flight number is mentioned as method parameter.*/
    public void searchPassengersByFlight(String flightNumber) {
        connectToDB();
        try (Statement searchByFlight = dbConnection.createStatement()) {
            searchByFlight.execute("select * from Passengers where FlightNumber=\'" + flightNumber + "\'");
            ResultSet searchPassenger = searchByFlight.getResultSet();
            JFrame frame = new JFrame("Passengers registered for flight " + flightNumber);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            String[] columnNames = {"Id", "FlightNumber", "FirstName", "LastName", "Sex", "Nationality", "DateOfBirth", "Passport", "ClassOfTrip"};
            while (searchPassenger.next()) {
                String passengerId = searchPassenger.getString("Id");
                String flight = searchPassenger.getString("FlightNumber");
                String name = searchPassenger.getString("FirstName");
                String surname = searchPassenger.getString("LastName");
                String sex = searchPassenger.getString("Sex");
                String nationality = searchPassenger.getString("Nationality");
                String dateOfBirth = searchPassenger.getString("DateOfBirth");
                String passport = searchPassenger.getString("Passport");
                String classOfTrip = searchPassenger.getString("ClassOfTrip");
                String[] oneRow = {passengerId, flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip};
                sortedByFlightPassengers.add(oneRow);
                passengers.add(new PassengersList(flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip));

            }

            for (PassengersList throughPassengers : passengers) {
                if (throughPassengers.getFlight().equals(flightNumber)) {
                    matches++;
                }
            }
            if (matches == 0) {
                JFrame jFrameNoPassengers = new JFrame();
                jFrameNoPassengers.setBounds(100,100,400,70);
                jFrameNoPassengers.setTitle("No results");
                jFrameNoPassengers.setResizable(false);
                jFrameNoPassengers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameNoPassengers.getContentPane();
                jFrameNoPassengers.setVisible(true);

                JPanel jPanelNoPassengers = new JPanel();
                jPanelNoPassengers.setLayout(new FlowLayout());
                jFrameNoPassengers.add(jPanelNoPassengers);

                JLabel jLabelNoFlights = new JLabel("Flight number is invalid or no registered passengers on this flight.");
                jPanelNoPassengers.add(jLabelNoFlights);
            }else {
                matches = 0;
                String[][] rowsOfTable = sortedByFlightPassengers.toArray(new String[sortedByFlightPassengers.size()][]);
                JTable table = new JTable(rowsOfTable, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                frame.getContentPane().add(scrollPane);
                frame.setPreferredSize(new Dimension(450, 200));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                sortedByFlightPassengers.clear();
                passengers.clear();
            }

        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }


    private void foundNoPassengers(){
        JFrame jFrameNoPassengers = new JFrame();
        jFrameNoPassengers.setBounds(100,100,250,60);
        jFrameNoPassengers.setTitle("No results");
        jFrameNoPassengers.setResizable(false);
        jFrameNoPassengers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameNoPassengers.getContentPane();
        jFrameNoPassengers.setVisible(true);

        JPanel jPanelNoPassengers = new JPanel();
        jPanelNoPassengers.setLayout(new FlowLayout());
        jFrameNoPassengers.add(jPanelNoPassengers);

        JLabel jLabelNoFlights = new JLabel("Passenger not found");
        jPanelNoPassengers.add(jLabelNoFlights);

    }


    private void resultSearchPassengers(String passengerId, String flight, String name, String surname,
                                        String sex, String nationality, String dateOfBirth, String passport, String classOfTrip){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 525, 300);
        jFrameSearchResult.setTitle("Flight found");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel labelId = new JLabel("Id");
        jPanelSearchResult.add(labelId);

        JTextField idTextField = new JTextField();
        idTextField.setText(passengerId);
        jPanelSearchResult.add(idTextField);

        JLabel labelFlight = new JLabel("FlightNumber");
        jPanelSearchResult.add(labelFlight);

        JTextField flightTextField = new JTextField();
        flightTextField.setText(flight);
        jPanelSearchResult.add(flightTextField);

        JLabel labelName = new JLabel("FirstName");
        jPanelSearchResult.add(labelName);

        JTextField nameTextField = new JTextField();
        nameTextField.setText(name);
        jPanelSearchResult.add(nameTextField);

        JLabel labelSurname = new JLabel("LastName");
        jPanelSearchResult.add(labelSurname);

        JTextField surnameTextField = new JTextField();
        surnameTextField.setText(surname);
        jPanelSearchResult.add(surnameTextField);

        JLabel labelSex = new JLabel("Sex");
        jPanelSearchResult.add(labelSex);

        JTextField sexTextField = new JTextField();
        sexTextField.setText(sex);
        jPanelSearchResult.add(sexTextField);

        JLabel labelNation = new JLabel("Nationality");
        jPanelSearchResult.add(labelNation);

        JTextField nationalityTF = new JTextField();
        nationalityTF.setText(nationality);
        jPanelSearchResult.add(nationalityTF);

        JLabel labelBirthday = new JLabel("DateOfBirth");
        jPanelSearchResult.add(labelBirthday);

        JTextField birthdayTF = new JTextField();
        birthdayTF.setText(dateOfBirth);
        jPanelSearchResult.add(birthdayTF);

        JLabel labelPassport = new JLabel("Passport");
        jPanelSearchResult.add(labelPassport);

        JTextField passportTF = new JTextField();
        passportTF.setText(passport);
        jPanelSearchResult.add(passportTF);

        JLabel labelClass = new JLabel("ClassOfTrip");
        jPanelSearchResult.add(labelClass);

        JTextField classTextField = new JTextField();
        classTextField.setText(classOfTrip);
        jPanelSearchResult.add(classTextField);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(labelId).addComponent(labelFlight).addComponent(labelName).addComponent(labelSurname)
                .addComponent(labelSex).addComponent(labelNation).addComponent(labelBirthday).addComponent(labelPassport)
                .addComponent(labelClass));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(idTextField).addComponent(flightTextField).addComponent(nameTextField).addComponent(surnameTextField)
                .addComponent(sexTextField).addComponent(nationalityTF).addComponent(birthdayTF)
                .addComponent(passportTF).addComponent(classTextField));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelId).addComponent(idTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelName).addComponent(nameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSurname).addComponent(surnameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSex).addComponent(sexTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelNation).addComponent(nationalityTF));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelBirthday).addComponent(birthdayTF));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelPassport).addComponent(passportTF));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelClass).addComponent(classTextField));
        searchResultLayout.setVerticalGroup(vGroup);
    }



    /**
     * Searches passenger by the first name. First name is mentioned as method parameter.*/
    public void searchPassengersByName(String firstName) {
        connectToDB();
        try (Statement searchByName = dbConnection.createStatement()) {
            searchByName.execute("select * from Passengers where FirstName=\'" + firstName + "\'");
            ResultSet searchPassenger = searchByName.getResultSet();
            while (searchPassenger.next()) {
                String passengerId = searchPassenger.getString("Id");
                String flight = searchPassenger.getString("FlightNumber");
                String name = searchPassenger.getString("FirstName");
                String surname = searchPassenger.getString("LastName");
                String sex = searchPassenger.getString("Sex");
                String nationality = searchPassenger.getString("Nationality");
                String dateOfBirth = searchPassenger.getString("DateOfBirth");
                String passport = searchPassenger.getString("Passport");
                String classOfTrip = searchPassenger.getString("ClassOfTrip");
                resultSearchPassengers(passengerId,flight,name,surname,sex,nationality,dateOfBirth,passport,classOfTrip);
                passengers.add(new PassengersList(flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip));

            }

            for (PassengersList throughPassengers : passengers) {
                if (throughPassengers.getFirstName().equals(firstName)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoPassengers();
            }
            matches = 0;
            passengers.clear();

        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Searches passenger by the last name. Last name is mentioned as method parameter.*/
    public void searchPassengersBySurname(String lastName) {
        connectToDB();
        try (Statement searchBySurname = dbConnection.createStatement()) {
            searchBySurname.execute("select * from Passengers where LastName=\'" + lastName + "\'");
            ResultSet searchPassenger = searchBySurname.getResultSet();
            while (searchPassenger.next()) {
                String passengerId = searchPassenger.getString("Id");
                String flight = searchPassenger.getString("FlightNumber");
                String name = searchPassenger.getString("FirstName");
                String surname = searchPassenger.getString("LastName");
                String sex = searchPassenger.getString("Sex");
                String nationality = searchPassenger.getString("Nationality");
                String dateOfBirth = searchPassenger.getString("DateOfBirth");
                String passport = searchPassenger.getString("Passport");
                String classOfTrip = searchPassenger.getString("ClassOfTrip");
                resultSearchPassengers(passengerId,flight,name,surname,sex,nationality,dateOfBirth,passport,classOfTrip);
                passengers.add(new PassengersList(flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip));
            }

            for (PassengersList throughPassengers : passengers) {
                if (throughPassengers.getLastName().equals(lastName)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoPassengers();
            }
            matches = 0;
            passengers.clear();


        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Searches passenger by the passport ID. Passport ID is mentioned as method parameter.*/
    public void searchPassengersByPassport(String passportId) {
        connectToDB();
        try (Statement searchByPassport = dbConnection.createStatement()) {
            searchByPassport.execute("select * from Passengers where Passport=\'" + passportId + "\'");
            ResultSet searchPassenger = searchByPassport.getResultSet();
            while (searchPassenger.next()) {
                String passengerId = searchPassenger.getString("Id");
                String flight = searchPassenger.getString("FlightNumber");
                String name = searchPassenger.getString("FirstName");
                String surname = searchPassenger.getString("LastName");
                String sex = searchPassenger.getString("Sex");
                String nationality = searchPassenger.getString("Nationality");
                String dateOfBirth = searchPassenger.getString("DateOfBirth");
                String passport = searchPassenger.getString("Passport");
                String classOfTrip = searchPassenger.getString("ClassOfTrip");
                resultSearchPassengers(passengerId,flight,name,surname,sex,nationality,dateOfBirth,passport,classOfTrip);
                passengers.add(new PassengersList(flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip));

            }

            for (PassengersList throughPassengers : passengers) {
                if (throughPassengers.getPassport().equals(passportId)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoPassengers();
            }
            matches = 0;
            passengers.clear();


        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Searches flights (arrivals and departures) by the city. City is mentioned as method parameter.*/
    public void searchFlightsByCity(String city) {
        connectToDB();
        try (Statement searchCity = dbConnection.createStatement()) {
            ResultSet searchArrival = searchCity.executeQuery("select * from Arrival where City=\'" + city + "\'");
            while (searchArrival.next()) {
                String flightNumber = searchArrival.getString("FlightNumber");
                String date = searchArrival.getString("Date");
                String time = searchArrival.getString("Time");
                String cityPort = searchArrival.getString("City");
                String status = searchArrival.getString("FlightStatus");
                String terminal = searchArrival.getString("Terminal");
                double distance = searchArrival.getDouble("Distance");
                String business = searchArrival.getString("BusinessPrice");
                String economy = searchArrival.getString("EconomyPrice");
                resultSearchFlights(flightNumber,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Arrival(date, cityPort, time, flightNumber, status, terminal, distance));
            }
            ResultSet searchDeparture = searchCity.executeQuery("select * from Departure where City=\'" + city + "\'");
            while (searchDeparture.next()) {
                String flightNumber = searchDeparture.getString("FlightNumber");
                String date = searchDeparture.getString("Date");
                String time = searchDeparture.getString("Time");
                String cityPort = searchDeparture.getString("City");
                String status = searchDeparture.getString("FlightStatus");
                String terminal = searchDeparture.getString("Terminal");
                double distance = searchDeparture.getDouble("Distance");
                String business = searchDeparture.getString("BusinessPrice");
                String economy = searchDeparture.getString("EconomyPrice");
                resultSearchFlights(flightNumber,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Departure(date, cityPort, time, flightNumber, status, terminal, distance));
            }

            for (FlightDirection throughFlights : flights) {
                if (throughFlights.getCity().equals(city)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoFlights();
            } else matches = 0;
            flights.clear();

        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }

    private void resultSearchFlights(String flightNumber, String date, String time, String city, String flightStatus, String terminal, String business, String economy){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 525, 300);
        jFrameSearchResult.setTitle("Flight found");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel labelFlight = new JLabel("FlightNumber");
        jPanelSearchResult.add(labelFlight);

        JTextField flightTextField = new JTextField();
        flightTextField.setText(flightNumber);
        jPanelSearchResult.add(flightTextField);

        JLabel labelDate = new JLabel("Date");
        jPanelSearchResult.add(labelDate);

        JTextField dateTextField = new JTextField();
        dateTextField.setText(date);
        jPanelSearchResult.add(dateTextField);

        JLabel labelTime = new JLabel("Time");
        jPanelSearchResult.add(labelTime);

        JTextField timeTextField = new JTextField();
        timeTextField.setText(time);
        jPanelSearchResult.add(timeTextField);

        JLabel labelCity = new JLabel("City");
        jPanelSearchResult.add(labelCity);

        JTextField cityTextField = new JTextField();
        cityTextField.setText(city);
        jPanelSearchResult.add(cityTextField);

        JLabel labelStatus = new JLabel("FlightStatus");
        jPanelSearchResult.add(labelStatus);

        JTextField statusTextField = new JTextField();
        statusTextField.setText(flightStatus);
        jPanelSearchResult.add(statusTextField);

        JLabel labelTerminal = new JLabel("Terminal");
        jPanelSearchResult.add(labelTerminal);

        JTextField terminalTextField = new JTextField();
        terminalTextField.setText(terminal);
        jPanelSearchResult.add(terminalTextField);

        JLabel labelBusiness = new JLabel("BusinessPrice");
        jPanelSearchResult.add(labelBusiness);

        JTextField priceBusinessTF = new JTextField();
        priceBusinessTF.setText(business);
        jPanelSearchResult.add(priceBusinessTF);

        JLabel labelEconomy = new JLabel("EconomyPrice");
        jPanelSearchResult.add(labelEconomy);

        JTextField priceEconomyTF = new JTextField();
        priceEconomyTF.setText(economy);
        jPanelSearchResult.add(priceEconomyTF);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(labelFlight).addComponent(labelDate).addComponent(labelTime).addComponent(labelCity)
                .addComponent(labelStatus).addComponent(labelTerminal).addComponent(labelBusiness).addComponent(labelEconomy));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(flightTextField).addComponent(dateTextField).addComponent(timeTextField).addComponent(cityTextField)
                .addComponent(statusTextField).addComponent(terminalTextField).addComponent(priceBusinessTF)
                .addComponent(priceEconomyTF));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDate).addComponent(dateTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTime).addComponent(timeTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCity).addComponent(cityTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelStatus).addComponent(statusTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTerminal).addComponent(terminalTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelBusiness).addComponent(priceBusinessTF));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelEconomy).addComponent(priceEconomyTF));
        searchResultLayout.setVerticalGroup(vGroup);
    }

    private void foundNoFlights(){
        JFrame jFrameNoFlights = new JFrame();
        jFrameNoFlights.setBounds(100,100,250,60);
        jFrameNoFlights.setTitle("No results");
        jFrameNoFlights.setResizable(false);
        jFrameNoFlights.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameNoFlights.getContentPane();
        jFrameNoFlights.setVisible(true);

        JPanel jPanelNoFlights = new JPanel();
        jPanelNoFlights.setLayout(new FlowLayout());
        jFrameNoFlights.add(jPanelNoFlights);

        JLabel jLabelNoFlights = new JLabel("Flight not found");
        jPanelNoFlights.add(jLabelNoFlights);
    }



    /**
     * Searches flights (arrivals and departures) by the flight number. Flight number is mentioned as method parameter.*/
    public void searchFlightsByNumber(String flightNumber) {
        connectToDB();
        try (Statement searchCity = dbConnection.createStatement()) {
            ResultSet searchArrival = searchCity.executeQuery("select * from Arrival where FlightNumber=\'" + flightNumber + "\'");
            while (searchArrival.next()) {
                String flight = searchArrival.getString("FlightNumber");
                String date = searchArrival.getString("Date");
                String time = searchArrival.getString("Time");
                String cityPort = searchArrival.getString("City");
                String status = searchArrival.getString("FlightStatus");
                String terminal = searchArrival.getString("Terminal");
                double distance = searchArrival.getDouble("Distance");
                String business = searchArrival.getString("BusinessPrice");
                String economy = searchArrival.getString("EconomyPrice");
                resultSearchFlights(flight,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Arrival(date, cityPort, time, flightNumber, status, terminal, distance));
            }
            ResultSet searchDeparture = searchCity.executeQuery("select * from Departure where FlightNumber=\'" + flightNumber + "\'");
            while (searchDeparture.next()) {
                String flight = searchDeparture.getString("FlightNumber");
                String date = searchDeparture.getString("Date");
                String time = searchDeparture.getString("Time");
                String cityPort = searchDeparture.getString("City");
                String status = searchDeparture.getString("FlightStatus");
                String terminal = searchDeparture.getString("Terminal");
                double distance = searchDeparture.getDouble("Distance");
                String business = searchDeparture.getString("BusinessPrice");
                String economy = searchDeparture.getString("EconomyPrice");
                resultSearchFlights(flight,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Departure(date, cityPort, time, flightNumber, status, terminal, distance));
            }

            for (FlightDirection throughFlights : flights) {
                if (throughFlights.getFlightNumber().equals(flightNumber)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoFlights();
            }
            matches = 0;
            flights.clear();

        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Searches flights (arrivals and departures) by the price. Price is mentioned as method parameter.
     * User or administrator should know the exact price in order to use this method effectively.*/
    public void searchFlightsByPrice(double price) {
        connectToDB();
        try (Statement statement = dbConnection.createStatement()) {
            ResultSet searchByPrice = statement.executeQuery("select * from Arrival where BusinessPrice=" + price + " or EconomyPrice=" + price);
            while (searchByPrice.next()) {
                String flightNumber = searchByPrice.getString("FlightNumber");
                String date = searchByPrice.getString("Date");
                String time = searchByPrice.getString("Time");
                String cityPort = searchByPrice.getString("City");
                String status = searchByPrice.getString("FlightStatus");
                String terminal = searchByPrice.getString("Terminal");
                double distance = searchByPrice.getDouble("Distance");
                String business = searchByPrice.getString("BusinessPrice");
                String economy = searchByPrice.getString("EconomyPrice");
                resultSearchFlights(flightNumber,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Arrival(date, cityPort, time, flightNumber, status, terminal, distance));
            }
            ResultSet searchByPrice2 = statement.executeQuery("select * from Departure where BusinessPrice=" + price + " or EconomyPrice=" + price);
            while (searchByPrice2.next()) {
                String flightNumber = searchByPrice2.getString("FlightNumber");
                String date = searchByPrice2.getString("Date");
                String time = searchByPrice2.getString("Time");
                String cityPort = searchByPrice2.getString("City");
                String status = searchByPrice2.getString("FlightStatus");
                String terminal = searchByPrice2.getString("Terminal");
                double distance = searchByPrice2.getDouble("Distance");
                String business = searchByPrice2.getString("BusinessPrice");
                String economy = searchByPrice2.getString("EconomyPrice");
                resultSearchFlights(flightNumber,date,time,cityPort,status,terminal,business,economy);
                flights.add(new FlightDirection().new Departure(date, cityPort, time, flightNumber, status, terminal, distance));
            }

            for (FlightDirection throughFlights : flights) {
                if (throughFlights.getPriceBusiness().equals(price)) {
                    matches++;
                }
                if (throughFlights.getPriceEconomy().equals(price)) {
                    matches++;
                }
            }
            if (matches == 0) {
                foundNoFlights();
            } else matches = 0;
            flights.clear();


        } catch (SQLException e) {
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }



    /**
     * Allows to update or delete records in Passengers table of the database.*/
    public void updatePassengersInfo(String field, String value) {
        switch (field){
            case "Flight number:":{
                searchPassengersByFlight(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        int Id = Integer.parseInt(idTextField.getText());
                        if (columnForUpdate.equals("Id")){
                            JFrame jFrameNoAction = new JFrame();
                            jFrameNoAction.setBounds(100,100,300,75);
                            jFrameNoAction.setTitle("Unsupportable operation");
                            jFrameNoAction.setResizable(false);
                            jFrameNoAction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFrameNoAction.getContentPane();
                            jFrameNoAction.setVisible(true);

                            JPanel jPanelNoAction = new JPanel();
                            jPanelNoAction.setLayout(new FlowLayout());
                            jFrameNoAction.add(jPanelNoAction);

                            JLabel jLabelDenied = new JLabel("Passenger's Id cannot be changed!");
                            jPanelNoAction.add(jLabelDenied);
                        }else {
                            connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("update Passengers set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and Id=" + Id);
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Passenger's record updated");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }
                        }
                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "First name:":{
                searchPassengersByName(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        int Id = Integer.parseInt(idTextField.getText());
                        if (columnForUpdate.equals("Id")){
                            JFrame jFrameNoAction = new JFrame();
                            jFrameNoAction.setBounds(100,100,300,75);
                            jFrameNoAction.setTitle("Unsupportable operation");
                            jFrameNoAction.setResizable(false);
                            jFrameNoAction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFrameNoAction.getContentPane();
                            jFrameNoAction.setVisible(true);

                            JPanel jPanelNoAction = new JPanel();
                            jPanelNoAction.setLayout(new FlowLayout());
                            jFrameNoAction.add(jPanelNoAction);

                            JLabel jLabelDenied = new JLabel("Passenger's Id cannot be changed!");
                            jPanelNoAction.add(jLabelDenied);
                        }else {
                            connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("update Passengers set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and Id=" + Id);
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Passenger's record updated");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }
                        }
                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "Last name:":{
                searchPassengersBySurname(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        int Id = Integer.parseInt(idTextField.getText());
                        if (columnForUpdate.equals("Id")){
                            JFrame jFrameNoAction = new JFrame();
                            jFrameNoAction.setBounds(100,100,300,75);
                            jFrameNoAction.setTitle("Unsupportable operation");
                            jFrameNoAction.setResizable(false);
                            jFrameNoAction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFrameNoAction.getContentPane();
                            jFrameNoAction.setVisible(true);

                            JPanel jPanelNoAction = new JPanel();
                            jPanelNoAction.setLayout(new FlowLayout());
                            jFrameNoAction.add(jPanelNoAction);

                            JLabel jLabelDenied = new JLabel("Passenger's Id cannot be changed!");
                            jPanelNoAction.add(jLabelDenied);
                        }else {
                            connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("update Passengers set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and Id=" + Id);
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Passenger's record updated");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }
                        }
                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "Passport ID:":{
                searchPassengersByPassport(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        int Id = Integer.parseInt(idTextField.getText());
                        if (columnForUpdate.equals("Id")){
                            JFrame jFrameNoAction = new JFrame();
                            jFrameNoAction.setBounds(100,100,300,75);
                            jFrameNoAction.setTitle("Unsupportable operation");
                            jFrameNoAction.setResizable(false);
                            jFrameNoAction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFrameNoAction.getContentPane();
                            jFrameNoAction.setVisible(true);

                            JPanel jPanelNoAction = new JPanel();
                            jPanelNoAction.setLayout(new FlowLayout());
                            jFrameNoAction.add(jPanelNoAction);

                            JLabel jLabelDenied = new JLabel("Passenger's Id cannot be changed!");
                            jPanelNoAction.add(jLabelDenied);
                        }else {
                            connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("update Passengers set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and Id=" + Id);
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Passenger's record updated");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }
                        }
                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }

    }



    /**
     * Allows to update or delete records in Arrival table of the database.*/
    public void updateArrivalsInfo(String field, String value) {
        switch (field){
            case "Flight number:":{
                searchFlightsByNumber(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm flight number:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        String flight = idTextField.getText();
                        connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("update Arrival set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and FlightNumber=\'" + flight + "\'");
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Arrival record updated");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }

                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "City/port:":{
                searchFlightsByCity(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm flight number:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("update Arrival set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Arrival record updated");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }
    }



    /**
     * Allows to update or delete records in Departure table of the database.*/
    public void updateDeparturesInfo(String field, String value){
        switch (field){
            case "Flight number:":{
                searchFlightsByNumber(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm flight number:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("update Departure set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Arrival record updated");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "City/port:":{
                searchFlightsByCity(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 200);
                jFrameUpdate.setTitle("Update information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel fieldLabel = new JLabel("Field to update:");
                jPanelUpdate.add(fieldLabel);

                JTextField fieldTextField = new JTextField();
                jPanelUpdate.add(fieldTextField);

                JLabel oldLabel = new JLabel("Old value:");
                jPanelUpdate.add(oldLabel);

                JTextField oldTextField = new JTextField();
                jPanelUpdate.add(oldTextField);

                JLabel newLabel = new JLabel("New value:");
                jPanelUpdate.add(newLabel);

                JTextField newTextField = new JTextField();
                jPanelUpdate.add(newTextField);

                JLabel idLabel = new JLabel("Confirm flight number:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel emptyLabel = new JLabel("");
                jPanelUpdate.add(emptyLabel);

                JButton updateButton = new JButton("Update record");
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String columnForUpdate = fieldTextField.getText();
                        String oldValue = oldTextField.getText();
                        String newValue = newTextField.getText();
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("update Departure set " + columnForUpdate + "=\'" + newValue + "\' where " + columnForUpdate + "=\'" + oldValue + "\' and FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Arrival record updated");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(updateButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldLabel).addComponent(oldLabel)
                        .addComponent(newLabel).addComponent(idLabel).addComponent(emptyLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(fieldTextField).addComponent(oldTextField)
                        .addComponent(newTextField).addComponent(idTextField).addComponent(updateButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fieldLabel).addComponent(fieldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(oldLabel).addComponent(oldTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(newLabel).addComponent(newTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(emptyLabel).addComponent(updateButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }
    }

    public void deletePassengers(String field, String value){
        switch (field){
            case "Flight number:":{
                searchPassengersByFlight(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete record");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int Id = Integer.parseInt(idTextField.getText());
                        connectToDB();
                            try (Statement updateColumn = dbConnection.createStatement()) {
                                updateColumn.execute("delete from Passengers where Id=" + Id);
                                JFrame jFramePassengerUpdate = new JFrame();
                                jFramePassengerUpdate.setBounds(100,100,300,90);
                                jFramePassengerUpdate.setTitle("Success");
                                jFramePassengerUpdate.setResizable(false);
                                jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                jFramePassengerUpdate.getContentPane();
                                jFramePassengerUpdate.setVisible(true);

                                JPanel jPanelUpdate = new JPanel();
                                jPanelUpdate.setLayout(new FlowLayout());
                                jFramePassengerUpdate.add(jPanelUpdate);

                                JLabel jLabelPassenger = new JLabel("Passenger's record deleted");
                                jPanelUpdate.add(jLabelPassenger);
                            } catch (SQLException ex) {
                                String message = ex.getMessage();
                                showSQLErrorMessage(message);
                            }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "First name:":{
                searchPassengersByName(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete record");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int Id = Integer.parseInt(idTextField.getText());
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("delete from Passengers where Id=" + Id);
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Passenger's record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "Last name:":{
                searchPassengersBySurname(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete record");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int Id = Integer.parseInt(idTextField.getText());
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("delete from Passengers where Id=" + Id);
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Passenger's record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "Passport ID:":{
                searchPassengersByPassport(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm passenger's Id:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete record");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int Id = Integer.parseInt(idTextField.getText());
                        connectToDB();
                        try (Statement updateColumn = dbConnection.createStatement()) {
                            updateColumn.execute("delete from Passengers where Id=" + Id);
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Passenger's record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }
    }

    public void deleteArrivals(String field, String value){
        switch (field){
            case "Flight number:":{
                searchFlightsByNumber(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm flight number for deletion:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement deleteRecord = dbConnection.createStatement()) {
                            deleteRecord.execute("delete from Arrival where FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Arrival record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "City/port:":{
                searchFlightsByCity(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm flight number for deletion:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement deleteRecord = dbConnection.createStatement()) {
                            deleteRecord.execute("delete from Arrival where FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Arrival record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }
    }

    public void deleteDepartures(String field, String value){
        switch (field){
            case "Flight number:":{
                searchFlightsByNumber(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm flight number for deletion:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement deleteRecord = dbConnection.createStatement()) {
                            deleteRecord.execute("delete from Departure where FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Departure record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
            case "City/port:":{
                searchFlightsByCity(value);
                JFrame jFrameUpdate = new JFrame();
                jFrameUpdate.setBounds(100, 100, 415, 97);
                jFrameUpdate.setTitle("Delete information");
                jFrameUpdate.setResizable(false);
                jFrameUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameUpdate.getContentPane();
                jFrameUpdate.setVisible(true);

                JPanel jPanelUpdate = new JPanel();
                GroupLayout updateLayout = new GroupLayout(jPanelUpdate);
                jPanelUpdate.setLayout(updateLayout);
                jFrameUpdate.add(jPanelUpdate);

                JLabel idLabel = new JLabel("Confirm flight number for deletion:");
                jPanelUpdate.add(idLabel);

                JTextField idTextField = new JTextField();
                jPanelUpdate.add(idTextField);

                JLabel warningLabel = new JLabel("");
                jPanelUpdate.add(warningLabel);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String flight = idTextField.getText();
                        connectToDB();
                        try (Statement deleteRecord = dbConnection.createStatement()) {
                            deleteRecord.execute("delete from Departure where FlightNumber=\'" + flight + "\'");
                            JFrame jFramePassengerUpdate = new JFrame();
                            jFramePassengerUpdate.setBounds(100,100,300,90);
                            jFramePassengerUpdate.setTitle("Success");
                            jFramePassengerUpdate.setResizable(false);
                            jFramePassengerUpdate.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jFramePassengerUpdate.getContentPane();
                            jFramePassengerUpdate.setVisible(true);

                            JPanel jPanelUpdate = new JPanel();
                            jPanelUpdate.setLayout(new FlowLayout());
                            jFramePassengerUpdate.add(jPanelUpdate);

                            JLabel jLabelPassenger = new JLabel("Departure record deleted");
                            jPanelUpdate.add(jLabelPassenger);
                        } catch (SQLException ex) {
                            String message = ex.getMessage();
                            showSQLErrorMessage(message);
                        }

                    }
                });
                jPanelUpdate.add(deleteButton);

                updateLayout.setAutoCreateGaps(true);
                updateLayout.setAutoCreateContainerGaps(true);
                GroupLayout.SequentialGroup hGroup = updateLayout.createSequentialGroup();
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idLabel).addComponent(warningLabel));
                hGroup.addGroup(updateLayout.createParallelGroup()
                        .addComponent(idTextField).addComponent(deleteButton));
                updateLayout.setHorizontalGroup(hGroup);
                GroupLayout.SequentialGroup vGroup = updateLayout.createSequentialGroup();
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(idLabel).addComponent(idTextField));
                vGroup.addGroup(updateLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(warningLabel).addComponent(deleteButton));
                updateLayout.setVerticalGroup(vGroup);
                break;
            }
        }
    }



    /**
     * Allows administrator to view passenger list of the specified flight number. Flight number is method parameter. */
    public void printPassengersList(String flightNumber){
        connectToDB();
        JFrame frame = new JFrame("Passengers registered for flight " + flightNumber);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        try(Statement searchPassengers = dbConnection.createStatement()){
            ResultSet sortedByFlight = searchPassengers.executeQuery("select * from Passengers where FlightNumber=\'" + flightNumber + "\'");
            String[] columnNames = {"Id", "FlightNumber", "FirstName", "LastName", "Sex", "Nationality", "DateOfBirth", "Passport", "ClassOfTrip"};
            while(sortedByFlight.next()){
                String passengerId = sortedByFlight.getString("Id");
                String flight = sortedByFlight.getString("FlightNumber");
                String name = sortedByFlight.getString("FirstName");
                String surname = sortedByFlight.getString("LastName");
                String sex = sortedByFlight.getString("Sex");
                String nationality = sortedByFlight.getString("Nationality");
                String dateOfBirth = sortedByFlight.getString("DateOfBirth");
                String passport = sortedByFlight.getString("Passport");
                String classOfTrip = sortedByFlight.getString("ClassOfTrip");
                String[] oneRow = {passengerId, flight, name, surname, sex, nationality, dateOfBirth, passport, classOfTrip};
                sortedByFlightPassengers.add(oneRow);
                passengers.add(new PassengersList(flight,name,surname,sex,nationality,dateOfBirth,passport,classOfTrip));

            }
            for (PassengersList throughPassengers: passengers){
                if (throughPassengers.getFlight().equals(flightNumber)){
                    matches++;
                }

            }
            if (matches==0){
                JFrame jFrameNoPassengers = new JFrame();
                jFrameNoPassengers.setBounds(100,100,400,70);
                jFrameNoPassengers.setTitle("No results");
                jFrameNoPassengers.setResizable(false);
                jFrameNoPassengers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameNoPassengers.getContentPane();
                jFrameNoPassengers.setVisible(true);

                JPanel jPanelNoPassengers = new JPanel();
                jPanelNoPassengers.setLayout(new FlowLayout());
                jFrameNoPassengers.add(jPanelNoPassengers);

                JLabel jLabelNoFlights = new JLabel("Flight number is invalid or no registered passengers on this flight.");
                jPanelNoPassengers.add(jLabelNoFlights);
            }else{
            matches=0;
            String[][] passengersTable = sortedByFlightPassengers.toArray(new String[sortedByFlightPassengers.size()][]);
            JTable jTable = new JTable(passengersTable, columnNames);
            JScrollPane scrollPane = new JScrollPane(jTable);
            frame.getContentPane().add(scrollPane);
            frame.setPreferredSize(new Dimension(450, 200));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            sortedByFlightPassengers.clear();
            }
        }catch (SQLException e){
            String message = e.getMessage();
            showSQLErrorMessage(message);
        }
    }

    private void showSQLErrorMessage(String message){
        JFrame jFrameSQLError = new JFrame();
        jFrameSQLError.setBounds(100,100,250,60);
        jFrameSQLError.setTitle("Database error");
        jFrameSQLError.setResizable(true);
        jFrameSQLError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSQLError.getContentPane();
        jFrameSQLError.setVisible(true);

        JPanel jPanelError = new JPanel();
        jPanelError.setLayout(new FlowLayout());
        jFrameSQLError.add(jPanelError);

        JLabel labelError = new JLabel(message);
        jPanelError.add(labelError);
    }

    private void showIllegalArgumentException(String message){
        JFrame jFrameSQLError = new JFrame();
        jFrameSQLError.setBounds(100,100,250,60);
        jFrameSQLError.setTitle("Incorrect input");
        jFrameSQLError.setResizable(true);
        jFrameSQLError.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSQLError.getContentPane();
        jFrameSQLError.setVisible(true);

        JPanel jPanelError = new JPanel();
        jPanelError.setLayout(new FlowLayout());
        jFrameSQLError.add(jPanelError);

        JLabel labelError = new JLabel(message);
        jPanelError.add(labelError);
    }
}

