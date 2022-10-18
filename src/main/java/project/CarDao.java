package project;

import java.util.List;
import java.util.Map;

public interface CarDao {
    public List<Car> getAllCompanies();

    public Car getCar(String input);

    public void updateCar(int id, String newName);

    public void deleteCar(int id);
}
