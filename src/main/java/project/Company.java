package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Company {

    private int id;
    private String name;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT id, name FROM cars WHERE company_id = " + this.id);
            while (result.next()) {
                cars.add(new Car(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve cars data");
            System.out.println(e.getMessage());
        }

        return cars;
    }

    public void chooseCar() {
        boolean quit = false;
        while (!quit) {
            printChooseCarInstructions();
            switch (Main.scanner.nextLine()) {
                case "1":
                    printCarList();
                    break;
                case "2":
                    createCar();
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input, please try again");
                    break;
            }
        }
    }

    private void printCarList() {
        List<Car> cars = getAllCars();
        if (cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            cars.forEach(car -> System.out.printf("%d. %s\n", car.getId(), car.getName()));
        }
    }

    private void createCar() {

    }

    private void printChooseCarInstructions() {
        System.out.printf("\n'%s' company\n", this.name);
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
