package project;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./database/carsharing");
        conn.setAutoCommit(true);
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS COMPANY (ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR NOT NULL)");
        conn.close();
    }

    private static void menuOptions() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            switch (scanner.nextLine()) {
                case "1":
                    logInAsManager(scanner);
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Incorrect option chosen :");
                    printMenuOptionsInstructions();
                    break;
            }
        }

        scanner.close();
    }

    private static void printMenuOptionsInstructions() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
    }

    private static void logInAsManager(Scanner scanner) {

    }
}