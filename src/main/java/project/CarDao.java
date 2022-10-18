package project;

import java.util.Map;

public interface CarDao {
    public Map<Integer, Car> getAllCompanies();
    public Car getCar(int id);
    public void updateCar(int id, String newName);
    public void deleteCar(int id);
}
