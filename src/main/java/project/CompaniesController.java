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
            printChooseCompanyInstructions(companies);
            String input = Main.scanner.nextLine();
            if (input.equals("0")) {
                return;
            }

            company = getCompany(input);
            if (company != null) {
                break;
            } else {
                System.out.println("There is no company with that name / id");
            }
        }

        company.chooseCar();
    }

    private Company getCompany(String input) {
        for (Company company : this.companies) {
            if (input.equals(company.getName()) || input.equals(String.valueOf(company.getId()))) {
                return company;
            }
        }

        return null;
    }

    private void printChooseCompanyInstructions(List<Company> companies) {
        System.out.println();
        System.out.println("Choose the company:");
        companies.forEach(company -> System.out.printf("%d. %s\n", company.getId(), company.getName()));
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
}
