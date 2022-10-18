package project;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static CompaniesController controller;
    private static Statement statement;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./database/carsharing");
        conn.setAutoCommit(true);
        statement = conn.createStatement();

        controller = new CompaniesController();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS company (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR NOT NULL)");
        menuOptions();
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
        boolean quit = false;
        while (!quit) {
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println(controller.toString());
                    break;
                case "2":
                    createCompany(scanner);
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input, please choose again:");
                    printLogInAsManagerInstructions();
            }
        }
    }

    private static void printLogInAsManagerInstructions() {
        System.out.println("1. Company List");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }

    private static void createCompany(Scanner scanner) {
        System.out.println("Enter the company name:");
        String input = scanner.nextLine();

        try {
            statement.executeUpdate("INSERT INTO COMPANY (name) VALUES (%s)", input.split(""));
        } catch (SQLException e) {
            System.out.println("Failed to add new company");
        }

    }
}