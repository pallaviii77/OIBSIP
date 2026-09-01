package com.railease;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CancellationFrame extends JFrame {

    private JTextField pnrField;
    private JTextArea detailsArea;
    private JButton cancelButton;

    private long currentPNR = -1;

    

    public CancellationFrame(String username) {

     
        setTitle("RailEase - Cancel Ticket");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // =====================================================
        // MAIN PANEL
        // =====================================================

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 35, 25, 35
                )
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel =
                new JLabel("Cancel Your Ticket");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        // =====================================================
        // SEARCH PANEL
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                10
                        )
                );

        JLabel pnrLabel =
                new JLabel("Enter PNR:");

        pnrLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        pnrField =
                new JTextField();

        pnrField.setPreferredSize(
                new Dimension(230, 35)
        );

        JButton fetchButton =
                new JButton("FETCH BOOKING");

        fetchButton.setPreferredSize(
                new Dimension(150, 35)
        );

        searchPanel.add(pnrLabel);
        searchPanel.add(pnrField);
        searchPanel.add(fetchButton);

        // =====================================================
        // TOP PANEL
        // =====================================================

        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.add(
                titleLabel,
                BorderLayout.NORTH
        );

        topPanel.add(
                searchPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // DETAILS
        // =====================================================

        detailsArea =
                new JTextArea();

        detailsArea.setEditable(false);

        detailsArea.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        15
                )
        );

        detailsArea.setLineWrap(false);

        detailsArea.setText(
                "Enter your PNR above and click "
                        + "\"FETCH BOOKING\" to view "
                        + "your reservation details."
        );

        JScrollPane scrollPane =
                new JScrollPane(detailsArea);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Booking Details"
                )
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        cancelButton =
                new JButton(
                        "CONFIRM CANCELLATION"
                );

        cancelButton.setPreferredSize(
                new Dimension(230, 40)
        );

        cancelButton.setEnabled(false);

        JButton backButton =
                new JButton(
                        "BACK TO DASHBOARD"
                );

        backButton.setPreferredSize(
                new Dimension(200, 40)
        );

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                15,
                                10
                        )
                );

        bottomPanel.add(cancelButton);
        bottomPanel.add(backButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =====================================================
        // FETCH
        // =====================================================

        fetchButton.addActionListener(
                e -> fetchBooking()
        );

        // =====================================================
        // CANCEL
        // =====================================================

        cancelButton.addActionListener(
                e -> cancelBooking()
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
    // FETCH BOOKING
    // =========================================================

    private void fetchBooking() {

        String pnrText =
                pnrField.getText().trim();

        if (pnrText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your PNR number.",
                    "PNR Required",
                    JOptionPane.WARNING_MESSAGE
            );

            pnrField.requestFocus();

            return;
        }

        long pnr;

        try {

            pnr =
                    Long.parseLong(pnrText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "PNR must contain only numbers.",
                    "Invalid PNR",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String sql =
                "SELECT * FROM reservations "
                + "WHERE pnr = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    pnr
            );

            ResultSet result =
                    statement.executeQuery();

            if (result.next()) {

                currentPNR = pnr;

                String details =
                        "================================================\n"
                        + "              RAILEASE BOOKING DETAILS\n"
                        + "================================================\n\n"
                        + "PNR              : "
                        + result.getLong("pnr") + "\n\n"
                        + "Passenger Name    : "
                        + result.getString(
                                "passenger_name"
                        ) + "\n\n"
                        + "Train Number      : "
                        + result.getInt(
                                "train_number"
                        ) + "\n\n"
                        + "Train Name        : "
                        + result.getString(
                                "train_name"
                        ) + "\n\n"
                        + "Class             : "
                        + result.getString(
                                "class_type"
                        ) + "\n\n"
                        + "Journey Date      : "
                        + result.getDate(
                                "journey_date"
                        ) + "\n\n"
                        + "Source Station    : "
                        + result.getString(
                                "source_station"
                        ) + "\n\n"
                        + "Destination       : "
                        + result.getString(
                                "destination_station"
                        ) + "\n\n"
                        + "Booking Status    : "
                        + result.getString(
                                "booking_status"
                        ) + "\n\n"
                        + "Booking Time      : "
                        + result.getTimestamp(
                                "booking_time"
                        ) + "\n\n"
                        + "================================================";

                detailsArea.setText(details);

                detailsArea.setCaretPosition(0);

                cancelButton.setEnabled(true);

            } else {

                currentPNR = -1;

                detailsArea.setText(
                        "No booking found for PNR: "
                                + pnr
                );

                cancelButton.setEnabled(false);

                JOptionPane.showMessageDialog(
                        this,
                        "No booking found for PNR: "
                                + pnr,
                        "Booking Not Found",
                        JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {

            showError(e);
        }
    }

    // =========================================================
    // CANCEL BOOKING
    // =========================================================

    private void cancelBooking() {

        if (currentPNR == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fetch a valid booking first."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel "
                                + "this ticket?\n\n"
                                + "PNR: "
                                + currentPNR,
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        String sql =
                "DELETE FROM reservations "
                + "WHERE pnr = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    currentPNR
            );

            int rowsDeleted =
                    statement.executeUpdate();

            if (rowsDeleted > 0) {

                detailsArea.setText(
                        "================================================\n"
                        + "                TICKET CANCELLED\n"
                        + "================================================\n\n"
                        + "PNR: "
                        + currentPNR
                        + "\n\n"
                        + "Your booking has been "
                        + "cancelled successfully.\n\n"
                        + "================================================"
                );

                JOptionPane.showMessageDialog(
                        this,
                        "Ticket cancelled successfully!\n\n"
                                + "PNR: "
                                + currentPNR,
                        "Cancellation Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );

                pnrField.setText("");

                cancelButton.setEnabled(false);

                currentPNR = -1;

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Booking could not be cancelled.",
                        "Cancellation Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {

            showError(e);
        }
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