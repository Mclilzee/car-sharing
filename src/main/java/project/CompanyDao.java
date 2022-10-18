package project;

import java.util.Map;

public interface CompanyDao {
    public Map<Integer, Company> getAllCompanies();
    public Company getCompany(int id);
    public void updateCompany(int id, String newName);
    public void deleteCompany(int id);
}
