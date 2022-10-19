package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CompaniesController {

    private static List<Company> companies;

    private CompaniesController() {
    }

    static {
        companies = new ArrayList<>();
    }

    private static void updateCompanies() {
        companies = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("Select * FROM company");
            while (result.next()) {
                companies.add(new Company(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve companies");
            System.out.println(e.getMessage());
        }
    }

    public static void chooseCompany() {
        updateCompanies();
        if (companies.isEmpty()) {
            System.out.println("\nThe company list is empty!");
            return;
        }

        Company company;
        while (true) {
            printChooseCompanyInstructions();
            String input = Main.scanner.nextLine();
            if (input.equals("0")) {
                return;
            }

            company = getCompany(input);
            if (company != null) {
                break;
            } else {
                System.out.println("There is no company with that name / id -> " + input);
            }
        }

        company.optionsMenu();
    }

    private static void printChooseCompanyInstructions() {
        System.out.println();
        System.out.println("Choose the company:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, companies.get(i).getName());
        }
        System.out.println("0. Back");
    }

    public static void createCompany() {
        System.out.println();
        System.out.println("Enter the company name:");
        String input = Main.scanner.nextLine();

        try {
            Main.getStatement().executeUpdate("INSERT INTO company (name) VALUES ('" + input + "')");
            System.out.println("The company was created!");
        } catch (SQLException e) {
            System.out.println("Failed to add new company");
            System.out.println(e.getMessage());
        }
    }

    private static Company getCompany(String input) {
        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input) - 1;
            return companies.size() > index ? companies.get(index) : null;
        } else {
            return findCompanyByName(input);
        }
    }

    private static Company findCompanyByName(String name) {
        for (Company company : companies) {
            if (name.equalsIgnoreCase(company.getName())) {
                return company;
            }
        }
        return null;
    }
}
