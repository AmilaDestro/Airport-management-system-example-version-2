package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The first window displayed during program execution.
 * Authorization is required to proceed.
 * @author Lyudmila Azarenko
 * @version 1.1*/

public final class LoginWindow {
    private static String username;
    private static String password;
    private JFrame frame;
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JPasswordField passwordField;

    public static void main (String... args) {
        LoginWindow window = new LoginWindow();
        window.frame.setVisible(true);
    }

    public LoginWindow () {
        initialize();
    }

    public void initialize() {
        frame = new JFrame ();
        frame.setBounds(100, 100, 250, 150);
        frame.setTitle("Authorization");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane();

        loginPanel = new JPanel();
        GroupLayout jPanelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(jPanelLayout);
        frame.add(loginPanel);

        JLabel appTitleLabel = new JLabel();
        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont((float)13));
        appTitleLabel.setText("");
        loginPanel.add(appTitleLabel);

        JLabel loginJLabel = new JLabel();
        loginJLabel.setFont(loginJLabel.getFont().deriveFont((float)12));
        loginJLabel.setText("Login :");
        loginPanel.add(loginJLabel);

        JLabel passwordJLabel = new JLabel();
        passwordJLabel.setFont(passwordJLabel.getFont().deriveFont((float)12));
        passwordJLabel.setText("Password :");
        loginPanel.add(passwordJLabel);

        loginTextField = new JTextField();
        loginPanel.add(loginTextField);

        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginButton = new JButton("Enter");
        ActionListener action = new LoginActionListener();
        loginButton.addActionListener(action);
        loginPanel.add(loginButton);

        jPanelLayout.setAutoCreateGaps(true);
        jPanelLayout.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = jPanelLayout.createSequentialGroup();
        hGroup.addGroup(jPanelLayout.createParallelGroup()
                .addComponent(loginJLabel).addComponent(passwordJLabel).addComponent(appTitleLabel));
        hGroup.addGroup(jPanelLayout.createParallelGroup()
                .addComponent(loginTextField).addComponent(passwordField).addComponent(loginButton));
        jPanelLayout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = jPanelLayout.createSequentialGroup();
        vGroup.addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(loginJLabel).addComponent(loginTextField));
        vGroup.addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordJLabel).addComponent(passwordField));
        vGroup.addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(appTitleLabel).addComponent(loginButton));
        jPanelLayout.setVerticalGroup(vGroup);
    }

    class LoginActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            password = String.valueOf(passwordField.getPassword());
            username = loginTextField.getText();
            String usernameCheck1 = "(admin)";
            String usernameCheck2 = "(user)";
            String userPasswordCheck = "(user)";
            String adminPasswordCheck = "(admin)";
            Pattern patternName1 = Pattern.compile(usernameCheck1);
            Pattern patternName2 = Pattern.compile(usernameCheck2);
            Pattern userPassword = Pattern.compile(userPasswordCheck);
            Pattern adminPassword = Pattern.compile(adminPasswordCheck);
            Matcher matcherName1 = patternName1.matcher(username);
            Matcher matcherName2 = patternName2.matcher(username);
            Matcher matcherUser = userPassword.matcher(password);
            Matcher matcherAdmin = adminPassword.matcher(password);
            if (matcherName1.matches()&&matcherAdmin.matches()){
                new AdminMenu();
                frame.setVisible(false);
            } else if (matcherName2.matches()&&matcherUser.matches()){
                new UserMenu();
                frame.setVisible(false);
            } else {
                JFrame menuFrame = new JFrame();
                menuFrame.setBounds(100, 100, 250, 60);
                menuFrame.setTitle("Authorization failed");
                menuFrame.setResizable(false);
                menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menuFrame.getContentPane().setLayout(new BorderLayout());
                menuFrame.setVisible(true);

                JPanel menuPanel = new JPanel();
                menuFrame.add(menuPanel);

                JLabel label = new JLabel("Wrong username or password.");
                menuPanel.add(label);
                frame.setVisible(false);

            }

        }
    }
}
