package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompaniesController implements CompanyDao {

    public CompaniesController() {
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("Select * FROM company");
            while (result.next()) {
                companies.add(new Company(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve companies");
            System.out.println(e.getMessage());
        }

        return companies;
    }

    @Override
    public Company getCompany(String input) {
        Company company;
        if (input.matches("\\d+")) {
            company = queryCompanyById(Integer.parseInt(input));
        } else {
            company = queryCompanyByName(input);
        }

        return company;
    }

    private Company queryCompanyByName(String name) {
        Company company = null;
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM company WHERE name = '" + name + "'");
            result.first();
            company = new Company(result.getInt("id"), result.getString("name"));
        } catch (SQLException ignored) {
        }

        return company;
    }

    private Company queryCompanyById(int id) {
        Company company = null;
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM company WHERE id = " + id);
            result.first();
            company = new Company(result.getInt("id"), result.getString("name"));
        } catch (SQLException ignored) {
        }

        return company;
    }

    public void chooseCompany() {
        List<Company> companies = this.getAllCompanies();
        if (companies.isEmpty()) {
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

            company = getCompany(companies, input);
            if (company != null) {
                break;
            } else {
                System.out.println("There is no company with that name / id");
            }
        }

        company.chooseCar();
    }

    private Company getCompany(List<Company> companies, String input) {
        for (Company company : companies) {
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

    @Override
    public void updateCompany(int id, String newName) {
    }

    @Override
    public void deleteCompany(int id) {
    }
}
