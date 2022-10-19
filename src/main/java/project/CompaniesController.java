package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesController {

    private List<Company> companies;

    public CompaniesController() {
        this.companies = new ArrayList<>();
    }

    private void updateCompanies() {
        this.companies = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("Select * FROM company");
            while (result.next()) {
                this.companies.add(new Company(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve companies");
            System.out.println(e.getMessage());
        }
    }

    public void chooseCompany() {
        updateCompanies();
        if (this.companies.isEmpty()) {
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

        company.chooseCar();
    }

    private void printChooseCompanyInstructions() {
        System.out.println();
        System.out.println("Choose the company:");
        for (int i = 0; i < companies.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, this.companies.get(i).getName());
        }
        System.out.println("0. Back");
    }

    public void createCompany() {
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

    private Company getCompany(String input) {
        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input) - 1;
            return this.companies.size() > index ? this.companies.get(index) : null;
        } else {
            return findCompanyByName(input);
        }
    }

    private Company findCompanyByName(String name) {
        for (Company company : this.companies) {
            if (name.equalsIgnoreCase(company.getName())) {
                return company;
            }
        }
        return null;
    }
}
