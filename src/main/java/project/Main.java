package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static CompaniesController controller;
    private static Statement statement;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:./database/carsharing");
        conn.setAutoCommit(true);
        statement = conn.createStatement();

        controller = new CompaniesController(statement);
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS company (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR UNIQUE NOT NULL)");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS cars    (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR UNIQUE NOT NULL," +
                "company_id INT NOT NULL, FOREIGN KEY (company_id) REFERENCES company(id))");
        menuOptions();
        conn.close();
    }

    private static void menuOptions() {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            printMenuOptionsInstructions();
            switch (scanner.nextLine()) {
                case "1":
                    logInAsManager(scanner);
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

    private static void logInAsManager(Scanner scanner) {
        boolean quit = false;
        while (!quit) {
            printLogInAsManagerInstructions();

            switch (scanner.nextLine()) {
                case "1":
                    chooseCompany(scanner);
                    break;
                case "2":
                    createCompany(scanner);
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

    private static void chooseCompany(Scanner scanner) {
        printChooseCompanyInstructions();
        Company company;
        while (true) {
            company = controller.getCompany(scanner.nextLine());

            if (company != null) {
                break;
            } else {
                System.out.println("There is no company with that name / id");
                printChooseCompanyInstructions();
            }
        }

        company.chooseCar(statement, scanner);
    }

    private static void printChooseCompanyInstructions() {
        System.out.println();
        System.out.println("Choose a company");
        System.out.println(controller.toString());
    }

    private static void createCompany(Scanner scanner) {
        System.out.println();
        System.out.println("Enter the company name:");
        String input = scanner.nextLine();

        try {
            statement.executeUpdate("INSERT INTO company (name) VALUES ('" + input + "')");
            System.out.println("The company was created!");
        } catch (SQLException e) {
            System.out.println("Failed to add new company");
            System.out.println(e.getMessage());
        }
    }
}