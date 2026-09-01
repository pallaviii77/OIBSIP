package com.railease;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("RailEase - Login");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("RailEase");
        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 32)
        );
        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Subtitle
        JLabel subtitleLabel =
                new JLabel("Smart Train Reservation System");

        subtitleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // Username
        JLabel usernameLabel =
                new JLabel("Username:");

        usernameField = new JTextField();

        // Password
        JLabel passwordLabel =
                new JLabel("Password:");

        passwordField =
                new JPasswordField();

        // Login button
        JButton loginButton =
                new JButton("LOGIN");

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        mainPanel.add(titleLabel, gbc);

        // Subtitle
        gbc.gridy = 1;

        mainPanel.add(subtitleLabel, gbc);

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        mainPanel.add(usernameLabel, gbc);

        // Username field
        gbc.gridx = 1;

        mainPanel.add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 3;

        mainPanel.add(passwordLabel, gbc);

        // Password field
        gbc.gridx = 1;

        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        mainPanel.add(loginButton, gbc);

        add(mainPanel);

        // Button action
        loginButton.addActionListener(
                e -> login()
        );

        setVisible(true);
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        // Basic validation
        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "SELECT * FROM users " +
                "WHERE username = ? AND password = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login successful!",
                        "Welcome",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

                new DashboardFrame(username);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Access Denied",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database connection error:\n"
                            + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}