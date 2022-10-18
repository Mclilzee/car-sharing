package project;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class CompaniesController implements CompanyDao {

    Map<Integer, Company> companies;

    public CompaniesController() {
        this.companies = new LinkedHashMap<>();
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

    @Override
    public String toString() {
        if (companies.isEmpty()) {
            return "The company list is empty!";
        }

        StringBuilder builder = new StringBuilder("Company List:\n");
        for (Map.Entry<Integer, Company> entry : companies.entrySet()) {
            builder.append(entry.getKey()).append(". ").append(entry.getValue().getName());
        }

        return builder.toString();
    }
}
