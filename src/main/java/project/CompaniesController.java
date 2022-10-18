package project;

import java.util.Map;
import java.util.Optional;

public class CompaniesController implements CompanyDao {

    Map<Integer, Company> companies;

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
}
