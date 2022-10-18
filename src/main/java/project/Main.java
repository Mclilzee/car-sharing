package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static CompaniesController controller;
    private static Statement statement;
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./database/carsharing");
        conn.setAutoCommit(true);
        statement = conn.createStatement();

        controller = new CompaniesController();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS company (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR UNIQUE NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS car (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR UNIQUE NOT NULL," +
                "company_id INT NOT NULL, FOREIGN KEY (company_id) REFERENCES company(id))");
        menuOptions();
        conn.close();
    }

    public static Statement getStatement() {
        return statement;
    }

    private static void menuOptions() {
        boolean quit = false;
        while (!quit) {
            printMenuOptionsInstructions();
            switch (scanner.nextLine()) {
                case "1":
                    logInAsManager();
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Incorrect option chosen :");
                    break;
            }
        }

        scanner.close();
    }

    private static void printMenuOptionsInstructions() {
        System.out.println("1. Log in as a manager");
        System.out.println("0. Exit");
    }

    private static void logInAsManager() {
        boolean quit = false;
        while (!quit) {
            printLogInAsManagerInstructions();

            switch (scanner.nextLine()) {
                case "1":
                    controller.chooseCompany();
                    break;
                case "2":
                    controller.createCompany();
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input, please choose again:");
                    break;
            }
        }
    }

    private static void printLogInAsManagerInstructions() {
        System.out.println("\n1. Company list");
        System.out.println("2. Create a company");
        System.out.println("0. Back");
    }


}