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
    public Company getCompany(int id) {
        Company company = null;
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM company WHERE id = " + id);
            result.first();
            company = new Company(result.getInt("id"), result.getString("name"));
        } catch (SQLException e) {
            System.out.println("Failed to retrieve company from database");
            System.out.println(e.getMessage());
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
