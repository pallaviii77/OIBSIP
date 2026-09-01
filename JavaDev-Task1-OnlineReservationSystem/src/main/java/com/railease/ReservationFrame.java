package com.railease;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class ReservationFrame extends JFrame {

    private JTextField passengerField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JTextField sourceField;
    private JTextField destinationField;

    private JSpinner dateSpinner;

    private JComboBox<String> classBox;

   

    public ReservationFrame(String username) {

     
        setTitle("RailEase - Book Ticket");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 25, 40
                )
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel =
                new JLabel("Book Your Journey");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        mainPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM PANEL
        // =====================================================

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // =====================================================
        // FIELDS
        // =====================================================

        passengerField =
                createTextField();

        trainNumberField =
                createTextField();

        trainNameField =
                createTextField();

        trainNameField.setEditable(false);

        sourceField =
                createTextField();

        sourceField.setEditable(false);

        destinationField =
                createTextField();

        destinationField.setEditable(false);

        // =====================================================
        // DATE
        // =====================================================

        SpinnerDateModel dateModel =
                new SpinnerDateModel(
                        new java.util.Date(),
                        new java.util.Date(),
                        null,
                        Calendar.DAY_OF_MONTH
                );

        dateSpinner =
                new JSpinner(dateModel);

        JSpinner.DateEditor dateEditor =
                new JSpinner.DateEditor(
                        dateSpinner,
                        "dd-MM-yyyy"
                );

        dateSpinner.setEditor(dateEditor);

        dateSpinner.setPreferredSize(
                new Dimension(250, 35)
        );

        // =====================================================
        // CLASS
        // =====================================================

        classBox =
                new JComboBox<>(
                        new String[]{
                                "AC First Class",
                                "AC 2 Tier",
                                "AC 3 Tier",
                                "Sleeper",
                                "Second Sitting"
                        }
                );

        classBox.setPreferredSize(
                new Dimension(250, 35)
        );

        // =====================================================
        // FIND TRAIN BUTTON
        // =====================================================

        JButton findTrainButton =
                new JButton("Find Train");

        findTrainButton.setPreferredSize(
                new Dimension(120, 35)
        );

        // =====================================================
        // BOOK BUTTON
        // =====================================================

        JButton bookButton =
                new JButton("BOOK TICKET");

        bookButton.setPreferredSize(
                new Dimension(200, 40)
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton backButton =
                new JButton("BACK TO DASHBOARD");

        backButton.setPreferredSize(
                new Dimension(200, 40)
        );

        // =====================================================
        // PASSENGER
        // =====================================================

        addField(
                formPanel,
                gbc,
                0,
                "Passenger Name:",
                passengerField
        );

        // =====================================================
        // TRAIN NUMBER
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        formPanel.add(
                new JLabel("Train Number:"),
                gbc
        );

        gbc.gridx = 1;

        formPanel.add(
                trainNumberField,
                gbc
        );

        gbc.gridx = 2;

        formPanel.add(
                findTrainButton,
                gbc
        );

        // =====================================================
        // TRAIN NAME
        // =====================================================

        addField(
                formPanel,
                gbc,
                2,
                "Train Name:",
                trainNameField
        );

        // =====================================================
        // CLASS
        // =====================================================

        addField(
                formPanel,
                gbc,
                3,
                "Class:",
                classBox
        );

        // =====================================================
        // DATE
        // =====================================================

        addField(
                formPanel,
                gbc,
                4,
                "Journey Date:",
                dateSpinner
        );

        // =====================================================
        // SOURCE
        // =====================================================

        addField(
                formPanel,
                gbc,
                5,
                "Source Station:",
                sourceField
        );

        // =====================================================
        // DESTINATION
        // =====================================================

        addField(
                formPanel,
                gbc,
                6,
                "Destination:",
                destinationField
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;

        formPanel.add(
                buttonPanel,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =====================================================
        // FIND TRAIN
        // =====================================================

        findTrainButton.addActionListener(
                e -> findTrain()
        );

        // =====================================================
        // BOOK
        // =====================================================

        bookButton.addActionListener(
                e -> bookTicket()
        );

        // =====================================================
        // BACK
        // =====================================================

        backButton.addActionListener(e -> {

            dispose();

            new DashboardFrame(username);
        });

        setVisible(true);
    }

    // =========================================================
    // CREATE TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setPreferredSize(
                new Dimension(250, 35)
        );

        return field;
    }

    // =========================================================
    // ADD FIELD
    // =========================================================

    private void addField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label,
            JComponent component) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(
                new JLabel(label),
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        panel.add(
                component,
                gbc
        );
    }

    // =========================================================
    // FIND TRAIN
    // =========================================================

    private void findTrain() {

        String trainText =
                trainNumberField.getText().trim();

        if (trainText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a train number.",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(trainText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train number must contain only numbers.",
                    "Invalid Train Number",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "SELECT train_name, source, destination "
                + "FROM trains WHERE train_number = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    trainNumber
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                trainNameField.setText(
                        result.getString("train_name")
                );

                sourceField.setText(
                        result.getString("source")
                );

                destinationField.setText(
                        result.getString("destination")
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Train found successfully!",
                        "Train Found",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {

                trainNameField.setText("");
                sourceField.setText("");
                destinationField.setText("");

                JOptionPane.showMessageDialog(
                        this,
                        "No train found with number "
                                + trainNumber,
                        "Train Not Found",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            showError(e);
        }
    }

    // =========================================================
    // BOOK TICKET
    // =========================================================

    private void bookTicket() {

        String passenger =
                passengerField.getText().trim();

        String trainText =
                trainNumberField.getText().trim();

        String trainName =
                trainNameField.getText().trim();

        String source =
                sourceField.getText().trim();

        String destination =
                destinationField.getText().trim();

        String classType =
                (String) classBox.getSelectedItem();

        if (passenger.isEmpty()
                || trainText.isEmpty()
                || trainName.isEmpty()
                || source.isEmpty()
                || destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all required fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!passenger.matches("[a-zA-Z ]+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passenger name should contain only letters.",
                    "Invalid Passenger Name",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(trainText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Train number must be numeric.",
                    "Invalid Train Number",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (source.equalsIgnoreCase(destination)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Source and destination cannot be the same.",
                    "Invalid Journey",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        java.util.Date selectedDate =
                (java.util.Date) dateSpinner.getValue();

        LocalDate journeyDate =
                selectedDate.toInstant()
                        .atZone(
                                java.time.ZoneId.systemDefault()
                        )
                        .toLocalDate();

        if (journeyDate.isBefore(
                LocalDate.now())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Journey date cannot be in the past.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Date sqlDate =
                Date.valueOf(journeyDate);

        String formattedDate =
                new SimpleDateFormat(
                        "dd-MM-yyyy"
                ).format(selectedDate);

        long pnr =
                generateUniquePNR();

        String sql =
                "INSERT INTO reservations "
                + "(pnr, passenger_name, train_number, "
                + "train_name, class_type, journey_date, "
                + "source_station, destination_station, "
                + "booking_status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(1, pnr);
            statement.setString(2, passenger);
            statement.setInt(3, trainNumber);
            statement.setString(4, trainName);
            statement.setString(5, classType);
            statement.setDate(6, sqlDate);
            statement.setString(7, source);
            statement.setString(8, destination);
            statement.setString(9, "CONFIRMED");

            statement.executeUpdate();

            String confirmation =
                    "========================================\n"
                    + "       RAILEASE BOOKING CONFIRMED\n"
                    + "========================================\n\n"
                    + "PNR             : " + pnr + "\n"
                    + "Passenger       : " + passenger + "\n"
                    + "Train Number    : " + trainNumber + "\n"
                    + "Train Name      : " + trainName + "\n"
                    + "Class           : " + classType + "\n"
                    + "Journey Date    : " + formattedDate + "\n"
                    + "From            : " + source + "\n"
                    + "To              : " + destination + "\n"
                    + "Status          : CONFIRMED\n\n"
                    + "========================================";

            JOptionPane.showMessageDialog(
                    this,
                    confirmation,
                    "Booking Confirmed",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

        } catch (Exception e) {

            showError(e);
        }
    }

    // =========================================================
    // UNIQUE PNR
    // =========================================================

    private long generateUniquePNR() {

        while (true) {

            long pnr =
                    ThreadLocalRandom.current()
                            .nextLong(
                                    1000000000L,
                                    9999999999L
                            );

            String sql =
                    "SELECT pnr FROM reservations "
                    + "WHERE pnr = ?";

            try (
                    Connection connection =
                            DBConnection.getConnection();

                    PreparedStatement statement =
                            connection.prepareStatement(sql)
            ) {

                statement.setLong(1, pnr);

                ResultSet result =
                        statement.executeQuery();

                if (!result.next()) {

                    return pnr;
                }

            } catch (Exception e) {

                showError(e);

                return pnr;
            }
        }
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        passengerField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        sourceField.setText("");
        destinationField.setText("");

        dateSpinner.setValue(
                new java.util.Date()
        );

        classBox.setSelectedIndex(0);
    }

    // =========================================================
    // ERROR
    // =========================================================

    private void showError(Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "Error:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}