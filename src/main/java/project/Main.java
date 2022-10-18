package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS cars    (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR UNIQUE NOT NULL," +
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
                    chooseCompany();
                    break;
                case "2":
                    createCompany();
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

    private static void chooseCompany() {
        List<Company> companies = controller.getAllCompanies();
        if (companies.isEmpty()) {
            System.out.println("\n The company list is empty!");
            return;
        }

        Company company;
        while (true) {
            printChooseCompanyInstructions(companies);
            company = getCompany(companies);

            if (company != null) {
                break;
            } else {
                System.out.println("There is no company with that name / id");
            }
        }

        company.chooseCar();
    }

    private static Company getCompany(List<Company> companies) {
        String input = scanner.nextLine();
        for (Company company : companies) {
            if (input.equals(company.getName()) || input.equals(String.valueOf(company.getId()))) {
                return company;
            }
        }

        return null;
    }

    private static void printChooseCompanyInstructions(List<Company> companies) {
        System.out.println();
        System.out.println("Choose the company:");
        companies.forEach(company -> System.out.printf("%d. %s\n", company.getId(), company.getName()));
    }

    private static void createCompany() {
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