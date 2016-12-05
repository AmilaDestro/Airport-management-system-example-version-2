package gui;

import com.brainacad.azarenko.airport.actions.Admin;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Administrator's window. Allows to view, create, search, update and delete records in the database*/

final class AdminMenu {
    private JFrame menuFrame;
    private JPanel menuPanel;
    private Admin admin = new Admin();
    private String selectedStatusArrival;
    private String selectedStatusDeparture;



    AdminMenu(){
        showAdminMenu();
    }

    private void showAdminMenu(){
        menuFrame = new JFrame();
        menuFrame.setBounds(100, 100, 480, 100);
        menuFrame.setTitle("Main menu");
        menuFrame.setResizable(false);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.getContentPane().setLayout(new FlowLayout());
        menuFrame.setVisible(true);

        menuPanel = new JPanel();
        menuFrame.add(menuPanel);

        JLabel greetings = new JLabel("You have authorized as admin");
        Font mainManuFont = new Font("Verdana", Font.PLAIN, 13);
        greetings.setFont(mainManuFont);
        greetings.setForeground(Color.RED);
        greetings.setHorizontalAlignment(JLabel.LEFT);
        greetings.setVerticalAlignment(JLabel.CENTER);
        menuFrame.getContentPane().add(greetings);

        JMenuBar menuBar = new JMenuBar();

        JMenu createRecord = new JMenu("Create record");

        JMenuItem newArrival = new JMenuItem("New arrival");
        createRecord.add(newArrival);
        newArrival.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createArrival();
            }
        });

        JMenuItem newDeparture = new JMenuItem("New departure");
        createRecord.add(newDeparture);
        newDeparture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDeparture();
            }
        });

        JMenuItem newPassenger = new JMenuItem("New passenger");
        createRecord.add(newPassenger);
        newPassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPassenger();
            }
        });

        menuBar.add(createRecord);
        menuFrame.setJMenuBar(menuBar);

        JMenu viewInfo = new JMenu("View information");

        JMenuItem arrivals = new JMenuItem("Arrivals");
        viewInfo.add(arrivals);
        ActionListener viewInfoArrivals = new ViewArrivals();
        arrivals.addActionListener(viewInfoArrivals);

        JMenuItem departures = new JMenuItem("Departures");
        viewInfo.add(departures);
        ActionListener viewInfoDepartures = new ViewDepartures();
        departures.addActionListener(viewInfoDepartures);

        JMenuItem passengers = new JMenuItem("Passengers");
        viewInfo.add(passengers);
        ActionListener viewInfoPassengers = new ViewPassengers();
        passengers.addActionListener(viewInfoPassengers);

        menuBar.add(viewInfo);

        JMenu searchInfo = new JMenu("Search information");

        JMenu searchFlights = new JMenu("Search flights");
        searchInfo.add(searchFlights);

        JMenuItem searchFByCity = new JMenuItem("By city/port");
        searchFlights.add(searchFByCity);
        searchFByCity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlightByCity();
            }
        });


        JMenuItem searchFByPrice = new JMenuItem("By price");
        searchFByPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlightsByPrice();
            }
        });
        searchFlights.add(searchFByPrice);

        JMenuItem searchFByNumber = new JMenuItem("By flight number");
        searchFByNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchFlightByNumber();
            }
        });
        searchFlights.add(searchFByNumber);

        JMenu searchPassengers = new JMenu("Search passengers");
        searchInfo.add(searchPassengers);

        JMenuItem searchPByFNumber = new JMenuItem("By flight number");
        searchPByFNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPassengersByFlight();
            }
        });
        searchPassengers.add(searchPByFNumber);

        JMenuItem searchPByName = new JMenuItem("By first name");
        searchPByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPassengerByName();
            }
        });
        searchPassengers.add(searchPByName);

        JMenuItem searchPBySurname = new JMenuItem("By last name");
        searchPBySurname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPassengerBySurname();
            }
        });
        searchPassengers.add(searchPBySurname);

        JMenuItem searchPByPassport = new JMenuItem("By passport");
        searchPByPassport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPassengerByPassport();
            }
        });
        searchPassengers.add(searchPByPassport);

        menuBar.add(searchInfo);

        JMenu updateRecords = new JMenu("Update/delete records");
        menuBar.add(updateRecords);

        JMenu specifiedActionUpdate = new JMenu("Update");
        JMenuItem updateArrival = new JMenuItem("Arrivals");
        updateArrival.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateArrivals();
            }
        });
        JMenuItem updateDeparture = new JMenuItem("Departures");
        updateDeparture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDepartures();
            }
        });
        JMenuItem updatePassenger = new JMenuItem("Passengers");
        updatePassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassengers();
            }
        });
        specifiedActionUpdate.add(updateArrival);
        specifiedActionUpdate.add(updateDeparture);
        specifiedActionUpdate.add(updatePassenger);

        JMenu specifiedActionDelete = new JMenu("Delete");
        JMenuItem deleteArrival = new JMenuItem("Arrivals");
        deleteArrival.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteArrivals();
            }
        });
        JMenuItem deleteDeparture = new JMenuItem("Departures");
        deleteDeparture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteDepartures();
            }
        });
        JMenuItem deletePassenger = new JMenuItem("Passengers");
        deletePassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePassengers();
            }
        });
        specifiedActionDelete.add(deleteArrival);
        specifiedActionDelete.add(deleteDeparture);
        specifiedActionDelete.add(deletePassenger);
        updateRecords.add(specifiedActionUpdate);
        updateRecords.add(specifiedActionDelete);


    }

    private void showPassengersInfo(){
        JFrame showPassengers = new JFrame();
        showPassengers.setBounds(100, 100, 300, 100);
        showPassengers.setTitle("View passengers list");
        showPassengers.setResizable(false);
        showPassengers.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showPassengers.getContentPane();

        showPassengers.getContentPane();
        showPassengers.setVisible(true);

        JPanel showPassengersPanel = new JPanel();
        GroupLayout passInfoGroupLayout = new GroupLayout(showPassengersPanel);
        showPassengersPanel.setLayout(passInfoGroupLayout);
        showPassengers.add(showPassengersPanel);

        JLabel labelFlight = new JLabel("Enter flight number:");
        showPassengersPanel.add(labelFlight);

        JLabel emptyLabel = new JLabel("");
        showPassengersPanel.add(emptyLabel);

        JTextField flightTextField = new JTextField();
        showPassengersPanel.add(flightTextField);

        JButton searchButton = new JButton("Search flight");
        showPassengersPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.printPassengersList(flightTextField.getText());
            }
        });

        passInfoGroupLayout.setAutoCreateGaps(true);
        passInfoGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = passInfoGroupLayout.createSequentialGroup();
        hGroup.addGroup(passInfoGroupLayout.createParallelGroup()
                .addComponent(labelFlight).addComponent(emptyLabel));
        hGroup.addGroup(passInfoGroupLayout.createParallelGroup()
                .addComponent(flightTextField).addComponent(searchButton));
        passInfoGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = passInfoGroupLayout.createSequentialGroup();
        vGroup.addGroup(passInfoGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(flightTextField));
        vGroup.addGroup(passInfoGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        passInfoGroupLayout.setVerticalGroup(vGroup);
    }

    private void createArrival(){
        JFrame newArrival = new JFrame();
        newArrival.setBounds(100, 100, 500, 280);
        newArrival.setTitle("Create new arrival record");
        newArrival.setResizable(false);
        newArrival.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newArrival.getContentPane();
        newArrival.setVisible(true);

        JPanel newArrivalPanel = new JPanel();
        GroupLayout newArrivalLayout = new GroupLayout(newArrivalPanel);
        newArrivalPanel.setLayout(newArrivalLayout);
        newArrival.add(newArrivalPanel);

        JLabel labelDate = new JLabel("Date (DAY MONTH YEAR)");
        newArrivalPanel.add(labelDate);

        JTextField dateTextField = new JFormattedTextField();
        newArrivalPanel.add(dateTextField);

        JLabel labelCity = new JLabel("City/port");
        newArrivalPanel.add(labelCity);

        JTextField cityTextField = new JFormattedTextField();
        newArrivalPanel.add(cityTextField);

        JLabel labelTime = new JLabel("Time");
        newArrivalPanel.add(labelTime);

        JTextField timeTextField = new JFormattedTextField();
        newArrivalPanel.add(timeTextField);

        JLabel labelFNumber = new JLabel("Flight number");
        newArrivalPanel.add(labelFNumber);

        JTextField fnumberTextField = new JFormattedTextField();
        newArrivalPanel.add(fnumberTextField);

        JLabel labelFStatus = new JLabel("Flight status");
        newArrivalPanel.add(labelFStatus);

        String[] items = {
                "CHECK_IN",
                "GATE_CLOSED",
                "ARRIVED",
                "DEPARTED_AT",
                "UNKNOWN",
                "CANCELED",
                "DELAYED",
                "IN_FLIGHT"
        };

        JComboBox chooseStatus = new JComboBox(items);
        chooseStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedStatusArrival = (String)chooseStatus.getSelectedItem();
            }
        });
        newArrivalPanel.add(chooseStatus);

        JLabel labelTerminal = new JLabel("Terminal");
        newArrivalPanel.add(labelTerminal);

        JTextField terminalTextField = new JTextField();
        newArrivalPanel.add(terminalTextField);

        JLabel labelDistance = new JLabel("Distance (in kilometers)");
        newArrivalPanel.add(labelDistance);

        JTextField distanceTextField = new JTextField();
        newArrivalPanel.add(distanceTextField);

        JLabel emptyLabel = new JLabel("");
        newArrivalPanel.add(emptyLabel);

        JButton createArrival = new JButton("Create record");
        newArrivalPanel.add(createArrival);
        createArrival.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double distance = Double.parseDouble(distanceTextField.getText());
                admin.createNewArrivalInfo(dateTextField.getText(), cityTextField.getText(),timeTextField.getText(),fnumberTextField.getText(),
                        selectedStatusArrival,terminalTextField.getText(),distance);
            }
        });

        newArrivalLayout.setAutoCreateGaps(true);
        newArrivalLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = newArrivalLayout.createSequentialGroup();
        hGroup.addGroup(newArrivalLayout.createParallelGroup()
                .addComponent(labelDate).addComponent(labelCity).addComponent(labelTime)
                .addComponent(labelFNumber).addComponent(labelFStatus).addComponent(labelTerminal)
                .addComponent(labelDistance).addComponent(emptyLabel));
        hGroup.addGroup(newArrivalLayout.createParallelGroup()
                .addComponent(dateTextField).addComponent(cityTextField).addComponent(timeTextField)
                .addComponent(fnumberTextField).addComponent(chooseStatus).addComponent(terminalTextField)
                .addComponent(distanceTextField).addComponent(createArrival));
        newArrivalLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = newArrivalLayout.createSequentialGroup();
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDate).addComponent(dateTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCity).addComponent(cityTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTime).addComponent(timeTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFNumber).addComponent(fnumberTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFStatus).addComponent(chooseStatus));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTerminal).addComponent(terminalTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDistance).addComponent(distanceTextField));
        vGroup.addGroup(newArrivalLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(createArrival));
        newArrivalLayout.setVerticalGroup(vGroup);


    }

    private void createDeparture(){
        JFrame newDeparture = new JFrame();
        newDeparture.setBounds(100, 100, 500, 280);
        newDeparture.setTitle("Create new departure record");
        newDeparture.setResizable(false);
        newDeparture.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newDeparture.getContentPane();
        newDeparture.setVisible(true);

        JPanel newDeparturePanel = new JPanel();
        GroupLayout newDepartureLayout = new GroupLayout(newDeparturePanel);
        newDeparturePanel.setLayout(newDepartureLayout);
        newDeparture.add(newDeparturePanel);

        JLabel labelDate = new JLabel("Date (DAY MONTH YEAR)");
        newDeparturePanel.add(labelDate);

        JTextField dateTextField = new JFormattedTextField();
        newDeparturePanel.add(dateTextField);

        JLabel labelCity = new JLabel("City/port");
        newDeparturePanel.add(labelCity);

        JTextField cityTextField = new JFormattedTextField();
        newDeparturePanel.add(cityTextField);

        JLabel labelTime = new JLabel("Time");
        newDeparturePanel.add(labelTime);

        JTextField timeTextField = new JFormattedTextField();
        newDeparturePanel.add(timeTextField);

        JLabel labelFNumber = new JLabel("Flight number");
        newDeparturePanel.add(labelFNumber);

        JTextField fnumberTextField = new JFormattedTextField();
        newDeparturePanel.add(fnumberTextField);

        JLabel labelFStatus = new JLabel("Flight status");
        newDeparturePanel.add(labelFStatus);

        String[] items = {
                "CHECK_IN",
                "GATE_CLOSED",
                "ARRIVED",
                "DEPARTED_AT",
                "UNKNOWN",
                "CANCELED",
                "DELAYED",
                "IN_FLIGHT"
        };

        JComboBox chooseStatus = new JComboBox(items);
        chooseStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedStatusDeparture = (String)chooseStatus.getSelectedItem();
            }
        });
        newDeparturePanel.add(chooseStatus);

        JLabel labelTerminal = new JLabel("Terminal");
        newDeparturePanel.add(labelTerminal);

        JTextField terminalTextField = new JTextField();
        newDeparturePanel.add(terminalTextField);

        JLabel labelDistance = new JLabel("Distance (in kilometers)");
        newDeparturePanel.add(labelDistance);

        JTextField distanceTextField = new JTextField();
        newDeparturePanel.add(distanceTextField);

        JLabel emptyLabel = new JLabel("");
        newDeparturePanel.add(emptyLabel);

        JButton createDeparture = new JButton("Create record");
        newDeparturePanel.add(createDeparture);
        createDeparture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double distance = Double.parseDouble(distanceTextField.getText());
                admin.createNewDepartureInfo(dateTextField.getText(), cityTextField.getText(),timeTextField.getText(),fnumberTextField.getText(),
                        selectedStatusDeparture,terminalTextField.getText(),distance);
            }
        });

        newDepartureLayout.setAutoCreateGaps(true);
        newDepartureLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = newDepartureLayout.createSequentialGroup();
        hGroup.addGroup(newDepartureLayout.createParallelGroup()
                .addComponent(labelDate).addComponent(labelCity).addComponent(labelTime)
                .addComponent(labelFNumber).addComponent(labelFStatus).addComponent(labelTerminal)
                .addComponent(labelDistance).addComponent(emptyLabel));
        hGroup.addGroup(newDepartureLayout.createParallelGroup()
                .addComponent(dateTextField).addComponent(cityTextField).addComponent(timeTextField)
                .addComponent(fnumberTextField).addComponent(chooseStatus).addComponent(terminalTextField)
                .addComponent(distanceTextField).addComponent(createDeparture));
        newDepartureLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = newDepartureLayout.createSequentialGroup();
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDate).addComponent(dateTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCity).addComponent(cityTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTime).addComponent(timeTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFNumber).addComponent(fnumberTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFStatus).addComponent(chooseStatus));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelTerminal).addComponent(terminalTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelDistance).addComponent(distanceTextField));
        vGroup.addGroup(newDepartureLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(createDeparture));
        newDepartureLayout.setVerticalGroup(vGroup);

    }

    private void createPassenger(){
        JFrame newPassenger = new JFrame();
        newPassenger.setBounds(100, 100, 525, 300);
        newPassenger.setTitle("Create new passenger record");
        newPassenger.setResizable(false);
        newPassenger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newPassenger.getContentPane();
        newPassenger.setVisible(true);

        JPanel newPassengerPanel = new JPanel();
        GroupLayout newPassengerLayout = new GroupLayout(newPassengerPanel);
        newPassengerPanel.setLayout(newPassengerLayout);
        newPassenger.add(newPassengerPanel);

        JLabel labelFlight = new JLabel("Flight number");
        newPassengerPanel.add(labelFlight);

        JTextField fnumberTextField = new JTextField();
        newPassengerPanel.add(fnumberTextField);

        JLabel labelName = new JLabel("First name");
        newPassengerPanel.add(labelName);

        JTextField nameTextField = new JTextField();
        newPassengerPanel.add(nameTextField);

        JLabel labelSurname = new JLabel("Last name");
        newPassengerPanel.add(labelSurname);

        JTextField surnameTextField = new JTextField();
        newPassengerPanel.add(surnameTextField);

        JLabel labelSex = new JLabel("Sex (MALE, FEMALE)");
        newPassengerPanel.add(labelSex);

        JTextField sexTextField = new JTextField();
        newPassengerPanel.add(sexTextField);

        JLabel labelNation = new JLabel("Nationality");
        newPassengerPanel.add(labelNation);

        JTextField nationalityTF = new JTextField();
        newPassengerPanel.add(nationalityTF);

        JLabel labelBirthday = new JLabel("Date of birth");
        newPassengerPanel.add(labelBirthday);

        JTextField birthdayTF = new JTextField();
        newPassengerPanel.add(birthdayTF);

        JLabel labelPassport = new JLabel("Passport ID");
        newPassengerPanel.add(labelPassport);

        JTextField passportTF = new JTextField();
        newPassengerPanel.add(passportTF);

        JLabel labelClass = new JLabel("Class (BUSINESS, ECONOMY)");
        newPassengerPanel.add(labelClass);

        JTextField classTextField = new JTextField();
        newPassengerPanel.add(classTextField);

        JLabel emptyLabel = new JLabel("");
        newPassengerPanel.add(emptyLabel);

        JButton createPassenger = new JButton("Create record");
        newPassengerPanel.add(createPassenger);
        createPassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.createListPassengers(fnumberTextField.getText(),nameTextField.getText(),surnameTextField.getText(),sexTextField.getText(),
                        nationalityTF.getText(),birthdayTF.getText(),passportTF.getText(),classTextField.getText());
            }
        });

        newPassengerLayout.setAutoCreateGaps(true);
        newPassengerLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = newPassengerLayout.createSequentialGroup();
        hGroup.addGroup(newPassengerLayout.createParallelGroup()
                .addComponent(labelFlight).addComponent(labelName).addComponent(labelSurname).addComponent(labelSex)
                .addComponent(labelNation).addComponent(labelBirthday).addComponent(labelPassport)
                .addComponent(labelClass).addComponent(emptyLabel));
        hGroup.addGroup(newPassengerLayout.createParallelGroup()
                .addComponent(fnumberTextField).addComponent(nameTextField).addComponent(surnameTextField).addComponent(sexTextField)
                .addComponent(nationalityTF).addComponent(birthdayTF).addComponent(passportTF)
                .addComponent(classTextField).addComponent(createPassenger));
        newPassengerLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = newPassengerLayout.createSequentialGroup();
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(fnumberTextField));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelName).addComponent(nameTextField));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSurname).addComponent(surnameTextField));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSex).addComponent(sexTextField));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelNation).addComponent(nationalityTF));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelBirthday).addComponent(birthdayTF));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelPassport).addComponent(passportTF));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelClass).addComponent(classTextField));
        vGroup.addGroup(newPassengerLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(createPassenger));
        newPassengerLayout.setVerticalGroup(vGroup);
    }

    private void searchFlightByCity(){
        JFrame frameByCity = new JFrame();
        frameByCity.setBounds(100, 100, 300, 100);
        frameByCity.setTitle("Search flights");
        frameByCity.setResizable(false);
        frameByCity.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByCity.getContentPane();

        frameByCity.getContentPane();
        frameByCity.setVisible(true);

        JPanel searchByCityPanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchByCityPanel);
        searchByCityPanel.setLayout(searchFlightGroupLayout);
        frameByCity.add(searchByCityPanel);

        JLabel labelCity = new JLabel("Enter city/port:");
        searchByCityPanel.add(labelCity);

        JLabel emptyLabel = new JLabel("");
        searchByCityPanel.add(emptyLabel);

        JTextField cityTextField = new JTextField();
        searchByCityPanel.add(cityTextField);

        JButton searchButton = new JButton("Search flight");
        searchByCityPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchFlightsByCity(cityTextField.getText());
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelCity).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(cityTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelCity).addComponent(cityTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchFlightsByPrice(){
        JFrame frameByPrice = new JFrame();
        frameByPrice.setBounds(100, 100, 300, 100);
        frameByPrice.setTitle("Search flights");
        frameByPrice.setResizable(false);
        frameByPrice.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByPrice.getContentPane();

        frameByPrice.getContentPane();
        frameByPrice.setVisible(true);

        JPanel searchByPricePanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchByPricePanel);
        searchByPricePanel.setLayout(searchFlightGroupLayout);
        frameByPrice.add(searchByPricePanel);

        JLabel labelPrice = new JLabel("Enter price:");
        searchByPricePanel.add(labelPrice);

        JLabel emptyLabel = new JLabel("");
        searchByPricePanel.add(emptyLabel);

        JTextField priceTextField = new JTextField();
        searchByPricePanel.add(priceTextField);

        JButton searchButton = new JButton("Search flight");
        searchByPricePanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double searchPrice = Double.parseDouble(priceTextField.getText());
                admin.searchFlightsByPrice(searchPrice);
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelPrice).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(priceTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelPrice).addComponent(priceTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchFlightByNumber(){
        JFrame frameByFNumber = new JFrame();
        frameByFNumber.setBounds(100, 100, 300, 100);
        frameByFNumber.setTitle("Search flights");
        frameByFNumber.setResizable(false);
        frameByFNumber.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByFNumber.getContentPane();

        frameByFNumber.getContentPane();
        frameByFNumber.setVisible(true);

        JPanel searchByFlightPanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchByFlightPanel);
        searchByFlightPanel.setLayout(searchFlightGroupLayout);
        frameByFNumber.add(searchByFlightPanel);

        JLabel labelFlight = new JLabel("Enter flight number:");
        searchByFlightPanel.add(labelFlight);

        JLabel emptyLabel = new JLabel("");
        searchByFlightPanel.add(emptyLabel);

        JTextField flightTextField = new JTextField();
        searchByFlightPanel.add(flightTextField);

        JButton searchButton = new JButton("Search flight");
        searchByFlightPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchFlightsByNumber(flightTextField.getText());
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelFlight).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(flightTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(flightTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchPassengersByFlight(){
        JFrame frameByFNumber = new JFrame();
        frameByFNumber.setBounds(100, 100, 300, 100);
        frameByFNumber.setTitle("Search passengers");
        frameByFNumber.setResizable(false);
        frameByFNumber.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByFNumber.getContentPane();

        frameByFNumber.getContentPane();
        frameByFNumber.setVisible(true);

        JPanel searchByFlightPanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchByFlightPanel);
        searchByFlightPanel.setLayout(searchFlightGroupLayout);
        frameByFNumber.add(searchByFlightPanel);

        JLabel labelFlight = new JLabel("Enter flight number:");
        searchByFlightPanel.add(labelFlight);

        JLabel emptyLabel = new JLabel("");
        searchByFlightPanel.add(emptyLabel);

        JTextField flightTextField = new JTextField();
        searchByFlightPanel.add(flightTextField);

        JButton searchButton = new JButton("Search passenger");
        searchByFlightPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchPassengersByFlight(flightTextField.getText());
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelFlight).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(flightTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelFlight).addComponent(flightTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchPassengerByName(){
        JFrame frameByName = new JFrame();
        frameByName.setBounds(100, 100, 300, 100);
        frameByName.setTitle("Search passengers");
        frameByName.setResizable(false);
        frameByName.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByName.getContentPane();

        frameByName.getContentPane();
        frameByName.setVisible(true);

        JPanel searchByNamePanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchByNamePanel);
        searchByNamePanel.setLayout(searchFlightGroupLayout);
        frameByName.add(searchByNamePanel);

        JLabel labelName = new JLabel("Enter first name:");
        searchByNamePanel.add(labelName);

        JLabel emptyLabel = new JLabel("");
        searchByNamePanel.add(emptyLabel);

        JTextField nameTextField = new JTextField();
        searchByNamePanel.add(nameTextField);

        JButton searchButton = new JButton("Search passenger");
        searchByNamePanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchPassengersByName(nameTextField.getText());
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelName).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(nameTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelName).addComponent(nameTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchPassengerBySurname(){
        JFrame frameBySurname = new JFrame();
        frameBySurname.setBounds(100, 100, 300, 100);
        frameBySurname.setTitle("Search passengers");
        frameBySurname.setResizable(false);
        frameBySurname.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameBySurname.getContentPane();

        frameBySurname.getContentPane();
        frameBySurname.setVisible(true);

        JPanel searchBySurnamePanel = new JPanel();
        GroupLayout searchFlightGroupLayout = new GroupLayout(searchBySurnamePanel);
        searchBySurnamePanel.setLayout(searchFlightGroupLayout);
        frameBySurname.add(searchBySurnamePanel);

        JLabel labelSurname = new JLabel("Enter last name:");
        searchBySurnamePanel.add(labelSurname);

        JLabel emptyLabel = new JLabel("");
        searchBySurnamePanel.add(emptyLabel);

        JTextField surnameTextField = new JTextField();
        searchBySurnamePanel.add(surnameTextField);

        JButton searchButton = new JButton("Search passenger");
        searchBySurnamePanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchPassengersBySurname(surnameTextField.getText());
            }
        });

        searchFlightGroupLayout.setAutoCreateGaps(true);
        searchFlightGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchFlightGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(labelSurname).addComponent(emptyLabel));
        hGroup.addGroup(searchFlightGroupLayout.createParallelGroup()
                .addComponent(surnameTextField).addComponent(searchButton));
        searchFlightGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchFlightGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelSurname).addComponent(surnameTextField));
        vGroup.addGroup(searchFlightGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchFlightGroupLayout.setVerticalGroup(vGroup);
    }

    private void searchPassengerByPassport(){
        JFrame frameByPassport = new JFrame();
        frameByPassport.setBounds(100, 100, 300, 100);
        frameByPassport.setTitle("Search passengers");
        frameByPassport.setResizable(false);
        frameByPassport.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameByPassport.getContentPane();

        frameByPassport.getContentPane();
        frameByPassport.setVisible(true);

        JPanel searchByPassportPanel = new JPanel();
        GroupLayout searchGroupLayout = new GroupLayout(searchByPassportPanel);
        searchByPassportPanel.setLayout(searchGroupLayout);
        frameByPassport.add(searchByPassportPanel);

        JLabel labelPassport = new JLabel("Enter passport Id:");
        searchByPassportPanel.add(labelPassport);

        JLabel emptyLabel = new JLabel("");
        searchByPassportPanel.add(emptyLabel);

        JTextField passportTextField = new JTextField();
        searchByPassportPanel.add(passportTextField);

        JButton searchButton = new JButton("Search passenger");
        searchByPassportPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin.searchPassengersByPassport(passportTextField.getText());
            }
        });

        searchGroupLayout.setAutoCreateGaps(true);
        searchGroupLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchGroupLayout.createSequentialGroup();
        hGroup.addGroup(searchGroupLayout.createParallelGroup()
                .addComponent(labelPassport).addComponent(emptyLabel));
        hGroup.addGroup(searchGroupLayout.createParallelGroup()
                .addComponent(passportTextField).addComponent(searchButton));
        searchGroupLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchGroupLayout.createSequentialGroup();
        vGroup.addGroup(searchGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(labelPassport).addComponent(passportTextField));
        vGroup.addGroup(searchGroupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel).addComponent(searchButton));
        searchGroupLayout.setVerticalGroup(vGroup);
    }


    private void updatePassengers(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 200);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel nameLabel = new JLabel("First name:");
        jPanelSearchResult.add(nameLabel);

        JTextField nameTextField = new JTextField();
        jPanelSearchResult.add(nameTextField);

        JLabel surnameLabel = new JLabel("Last name:");
        jPanelSearchResult.add(surnameLabel);

        JTextField surnameTextField = new JTextField();
        jPanelSearchResult.add(surnameTextField);

        JLabel passportLabel = new JLabel("Passport ID:");
        jPanelSearchResult.add(passportLabel);

        JTextField passportTextField = new JTextField();
        jPanelSearchResult.add(passportTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.updatePassengersInfo(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = nameLabel.getText();
                    String value = nameTextField.getText();
                    admin.updatePassengersInfo(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        !surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = surnameLabel.getText();
                    String value = surnameTextField.getText();
                    admin.updatePassengersInfo(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&!passportTextField.getText().isEmpty()){
                    String field = passportLabel.getText();
                    String value = passportTextField.getText();
                    admin.updatePassengersInfo(field, value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel).addComponent(nameLabel).addComponent(surnameLabel)
                .addComponent(passportLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField).addComponent(nameTextField).addComponent(surnameTextField)
                .addComponent(passportTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nameLabel).addComponent(nameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(surnameLabel).addComponent(surnameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passportLabel).addComponent(passportTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);
    }

    private void updateArrivals(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 150);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel cityLabel = new JLabel("City/port:");
        jPanelSearchResult.add(cityLabel);

        JTextField cityTextField = new JTextField();
        jPanelSearchResult.add(cityTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&cityTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.updateArrivalsInfo(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!cityTextField.getText().isEmpty()){
                    String field = cityLabel.getText();
                    String value = cityTextField.getText();
                    admin.updateArrivalsInfo(field,value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel)
                .addComponent(cityLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField)
                .addComponent(cityTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(cityLabel).addComponent(cityTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);

    }

    private void updateDepartures(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 150);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel cityLabel = new JLabel("City/port:");
        jPanelSearchResult.add(cityLabel);

        JTextField cityTextField = new JTextField();
        jPanelSearchResult.add(cityTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&cityTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.updateDeparturesInfo(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!cityTextField.getText().isEmpty()){
                    String field = cityLabel.getText();
                    String value = cityTextField.getText();
                    admin.updateDeparturesInfo(field,value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel)
                .addComponent(cityLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField)
                .addComponent(cityTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(cityLabel).addComponent(cityTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);

    }

    private void deleteArrivals(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 150);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel cityLabel = new JLabel("City/port:");
        jPanelSearchResult.add(cityLabel);

        JTextField cityTextField = new JTextField();
        jPanelSearchResult.add(cityTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&cityTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.deleteArrivals(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!cityTextField.getText().isEmpty()){
                    String field = cityLabel.getText();
                    String value = cityTextField.getText();
                    admin.deleteArrivals(field,value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel)
                .addComponent(cityLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField)
                .addComponent(cityTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(cityLabel).addComponent(cityTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);
    }

    private void deleteDepartures(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 150);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel cityLabel = new JLabel("City/port:");
        jPanelSearchResult.add(cityLabel);

        JTextField cityTextField = new JTextField();
        jPanelSearchResult.add(cityTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&cityTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.deleteDepartures(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!cityTextField.getText().isEmpty()){
                    String field = cityLabel.getText();
                    String value = cityTextField.getText();
                    admin.deleteDepartures(field,value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel)
                .addComponent(cityLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField)
                .addComponent(cityTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(cityLabel).addComponent(cityTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);
    }

    private void deletePassengers(){
        JFrame jFrameSearchResult = new JFrame();
        jFrameSearchResult.setBounds(100, 100, 415, 200);
        jFrameSearchResult.setTitle("Specify search parameter");
        jFrameSearchResult.setResizable(false);
        jFrameSearchResult.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrameSearchResult.getContentPane();
        jFrameSearchResult.setVisible(true);

        JPanel jPanelSearchResult = new JPanel();
        GroupLayout searchResultLayout = new GroupLayout(jPanelSearchResult);
        jPanelSearchResult.setLayout(searchResultLayout);
        jFrameSearchResult.add(jPanelSearchResult);

        JLabel fillingLabel = new JLabel("Fill in one of search fields");
        jPanelSearchResult.add(fillingLabel);

        JLabel emptyLabel = new JLabel("                            ");
        jPanelSearchResult.add(emptyLabel);

        JLabel flightLabel = new JLabel("Flight number:");
        jPanelSearchResult.add(flightLabel);

        JTextField flightTextField = new JTextField();
        jPanelSearchResult.add(flightTextField);

        JLabel nameLabel = new JLabel("First name:");
        jPanelSearchResult.add(nameLabel);

        JTextField nameTextField = new JTextField();
        jPanelSearchResult.add(nameTextField);

        JLabel surnameLabel = new JLabel("Last name:");
        jPanelSearchResult.add(surnameLabel);

        JTextField surnameTextField = new JTextField();
        jPanelSearchResult.add(surnameTextField);

        JLabel passportLabel = new JLabel("Passport ID:");
        jPanelSearchResult.add(passportLabel);

        JTextField passportTextField = new JTextField();
        jPanelSearchResult.add(passportTextField);

        JLabel emptyLabel2 = new JLabel("");
        jPanelSearchResult.add(emptyLabel2);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = flightLabel.getText();
                    String value = flightTextField.getText();
                    admin.deletePassengers(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&!nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = nameLabel.getText();
                    String value = nameTextField.getText();
                    admin.deletePassengers(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        !surnameTextField.getText().isEmpty()&&passportTextField.getText().isEmpty()){
                    String field = surnameLabel.getText();
                    String value = surnameTextField.getText();
                    admin.deletePassengers(field, value);
                } else
                if (flightTextField.getText().isEmpty()&&nameTextField.getText().isEmpty()&&
                        surnameTextField.getText().isEmpty()&&!passportTextField.getText().isEmpty()){
                    String field = passportLabel.getText();
                    String value = passportTextField.getText();
                    admin.deletePassengers(field, value);
                } else {
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

                    JLabel jLabelDenied = new JLabel("Specify only one search parameter.");
                    jPanelNoAction.add(jLabelDenied);
                }
            }
        });
        jPanelSearchResult.add(searchButton);

        searchResultLayout.setAutoCreateGaps(true);
        searchResultLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = searchResultLayout.createSequentialGroup();
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(fillingLabel).addComponent(flightLabel).addComponent(nameLabel).addComponent(surnameLabel)
                .addComponent(passportLabel).addComponent(emptyLabel2));
        hGroup.addGroup(searchResultLayout.createParallelGroup()
                .addComponent(emptyLabel).addComponent(flightTextField).addComponent(nameTextField).addComponent(surnameTextField)
                .addComponent(passportTextField).addComponent(searchButton));
        searchResultLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = searchResultLayout.createSequentialGroup();
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(fillingLabel).addComponent(emptyLabel));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(flightLabel).addComponent(flightTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(nameLabel).addComponent(nameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(surnameLabel).addComponent(surnameTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passportLabel).addComponent(passportTextField));
        vGroup.addGroup(searchResultLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(emptyLabel2).addComponent(searchButton));

        searchResultLayout.setVerticalGroup(vGroup);

    }


    class ViewArrivals implements ActionListener{
        public void actionPerformed(ActionEvent e){
            admin.printArrivalInfo();
        }
    }

    class ViewDepartures implements ActionListener{
        public void actionPerformed(ActionEvent e){
            admin.printDepartureInfo();
        }
    }

    class ViewPassengers implements ActionListener{
        public void actionPerformed(ActionEvent e){
            showPassengersInfo();
        }
    }

}
