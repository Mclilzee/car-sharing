package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompaniesController implements CompanyDao {
    private final Statement statement;

    public CompaniesController(Statement statement) {
        this.statement = statement;
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("Select * FROM company");
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
            ResultSet result = statement.executeQuery("SELECT * FROM company WHERE name = '" + name + "'");
            result.first();
            company = new Company(result.getInt("id"), result.getString("name"));
        } catch (SQLException ignored) {
        }

        return company;
    }

    private Company queryCompanyById(int id) {
        Company company = null;
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM company WHERE id = " + id);
            result.first();
            company = new Company(result.getInt("id"), result.getString("name"));
        } catch (SQLException ignored) {
        }

        return company;
    }

    @Override
    public void updateCompany(int id, String newName) {
    }

    @Override
    public void deleteCompany(int id) {
    }
}
