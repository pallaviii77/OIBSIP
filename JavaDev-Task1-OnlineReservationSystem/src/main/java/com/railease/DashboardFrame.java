package com.railease;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

   

    public DashboardFrame(String username) {

    

        setTitle("RailEase - Dashboard");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel =
                new JPanel(new BorderLayout(20, 20));

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel titleLabel =
                new JLabel("RailEase");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 32)
        );

        titleLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        JLabel welcomeLabel =
                new JLabel("Welcome, " + username);

        welcomeLabel.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        welcomeLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        headerPanel.add(titleLabel);
        headerPanel.add(welcomeLabel);

        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(2, 2, 20, 20)
                );

        JButton bookButton =
                new JButton("Book Train Ticket");

        JButton cancelButton =
                new JButton("Cancel Ticket");

        JButton logoutButton =
                new JButton("Logout");

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);

        // =====================================================
        // ADD TO MAIN PANEL
        // =====================================================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =====================================================
        // BOOK BUTTON
        // =====================================================

        bookButton.addActionListener(e -> {

            dispose();

            new ReservationFrame(username);
        });

        // =====================================================
        // CANCEL BUTTON
        // =====================================================

        cancelButton.addActionListener(e -> {

            dispose();

            new CancellationFrame(username);
        });

        // =====================================================
        // LOGOUT BUTTON
        // =====================================================

        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice == JOptionPane.YES_OPTION) {

                dispose();

                new LoginFrame();
            }
        });

        setVisible(true);
    }
}