# Online Reservation System

## Project Description

The Online Reservation System is a Java-based application developed to simplify railway ticket reservation and cancellation. The system provides a graphical user interface for users to log in, access the dashboard, make reservations, view reservation details, and cancel tickets.

The project uses Java Swing for the graphical user interface and MySQL database connectivity for storing and managing reservation-related data.

## Features

- User login and authentication
- User dashboard
- Railway ticket reservation
- Passenger and journey details management
- Reservation details display
- Ticket cancellation
- Database connectivity
- Graphical User Interface (GUI)
- Separate screens for different operations

## Technologies Used

- Java
- Java Swing
- MySQL
- JDBC
- Object-Oriented Programming (OOP)
- VS Code

## Project Structure

```text
JavaDev-Task1-OnlineReservationSystem/
│
├── src/
│   ├── Main.java
│   ├── LoginFrame.java
│   ├── DashboardFrame.java
│   ├── ReservationFrame.java
│   ├── CancellationFrame.java
│   └── DBConnection.java
│
├── screenshots/
│   └── Application screenshots
│
└── README.md

Description of Classes
Main.java

The entry point of the application that starts the Online Reservation System.

LoginFrame.java

Provides the login interface and handles user authentication.

DashboardFrame.java

Displays the main dashboard and provides access to the different reservation system operations.

ReservationFrame.java

Provides the interface for entering passenger and journey details and making a reservation.

CancellationFrame.java

Allows users to cancel an existing reservation.

DBConnection.java

Handles the connection between the Java application and the MySQL database using JDBC.

How to Run
Install Java JDK on your system.
Install and configure MySQL.
Create the required database and tables.
Update the database connection details in DBConnection.java.
Open the project in VS Code.
Compile the Java source files.
Run Main.java.
Login and use the available reservation system features.
Database

The application uses MySQL to store and manage reservation-related information.

JDBC is used to establish communication between the Java application and the MySQL database.

Objective

The objective of this project is to develop a user-friendly railway reservation system using Java GUI and database connectivity while demonstrating practical implementation of Object-Oriented Programming, Java Swing, JDBC, and MySQL.

Screenshots

Screenshots demonstrating the working application are included in the screenshots folder.

Author

Pallavi G