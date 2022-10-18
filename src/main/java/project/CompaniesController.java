package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CompaniesController implements CompanyDao {

    private Map<Integer, Company> companies;
    private final Statement statement;

    public CompaniesController(Statement statement) {
        this.companies = new LinkedHashMap<>();
        this.statement = statement;
        try {
            updateCompanies();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public Map<Integer, Company> getAllCompanies() {
        return companies;
    }

    @Override
    public Company getCompany(int id) {
        return companies.get(id);
    }

    @Override
    public void updateCompany(int id, String newName) {
        Optional<Company> companyOptional = Optional.ofNullable(companies.get(id));
        companyOptional.ifPresent(company -> company.setName(newName));
    }

    @Override
    public void deleteCompany(int id) {
        companies.remove(id);
    }

    public void updateCompanies() throws SQLException {
        ResultSet result = queryCompanies();

        Map<Integer, Company> companyMap = new LinkedHashMap<>();
        while (result.next()) {
            int id = result.getInt("id");
            String name = result.getString("name");

            companyMap.put(id, new Company(id, name));
        }

        companies = companyMap;
    }

    private ResultSet queryCompanies() throws SQLException {
        try {
            return statement.executeQuery("SELECT * FROM company;");
        } catch (SQLException ignored) {
        }

        throw new SQLException();
    }

    @Override
    public String toString() {
        if (companies.isEmpty()) {
            return "The company list is empty!";
        }

        StringBuilder builder = new StringBuilder("Company List:\n");
        for (Map.Entry<Integer, Company> entry : companies.entrySet()) {
            builder.append(entry.getKey()).append(". ").append(entry.getValue().getName()).append("\n");
        }

        return builder.toString().strip();
    }
}
