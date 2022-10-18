package project;

import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies();
    public Company getCompany(int id);
    public void updateCompany(int id, String newName);
    public void deleteCompany(int id);
}
