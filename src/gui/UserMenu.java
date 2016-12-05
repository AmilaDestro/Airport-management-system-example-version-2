package gui;

import com.brainacad.azarenko.airport.actions.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User's window. Allows to view and search information about arrivals and departures.*/

final class UserMenu {
    private JFrame menuFrame;
    private JPanel menuPanel;
    private User user = new User();

    UserMenu(){
        showUserMenu();
    }

    private void showUserMenu(){
        menuFrame = new JFrame();
        menuFrame.setBounds(100, 100, 250, 100);
        menuFrame.setTitle("Main menu");
        menuFrame.setResizable(false);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.getContentPane().setLayout(new BorderLayout());
        menuFrame.setVisible(true);

        menuPanel = new JPanel();
        menuFrame.add(menuPanel);

        JLabel greetings = new JLabel("You have authorized as user");
        Font mainManuFont = new Font("Verdana", Font.PLAIN, 13);
        greetings.setFont(mainManuFont);
        greetings.setForeground(Color.BLUE);
        greetings.setHorizontalAlignment(JLabel.CENTER);
        greetings.setVerticalAlignment(JLabel.CENTER);
        menuFrame.getContentPane().add(greetings);

        JMenuBar menuBar = new JMenuBar();
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

        menuBar.add(viewInfo);
        viewInfo.setHorizontalAlignment(JMenu.CENTER);

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
        menuBar.add(searchInfo);
        searchInfo.setHorizontalAlignment(JMenu.CENTER);
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
                user.searchFlightsByCity(cityTextField.getText());
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
                user.searchFlightsByPrice(searchPrice);
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
                user.searchFlightsByNumber(flightTextField.getText());
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


    class ViewArrivals implements ActionListener{
        public void actionPerformed(ActionEvent e){
            user.printArrivalInfo();
        }
    }

    class ViewDepartures implements ActionListener{
        public void actionPerformed(ActionEvent e){
            user.printDepartureInfo();
        }
    }

}
