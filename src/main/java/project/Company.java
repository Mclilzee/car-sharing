package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Company {

    private final int id;
    private final String name;
    private List<Car> cars;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.cars = new ArrayList<>();
    }

    public List<Car> getAvailableCars() {
        updateCars();
        List<Integer> rentedCarsId = getRentedCarsId();
        List<Car> availableList = new ArrayList<>(this.cars);
        for (int id : rentedCarsId) {
            availableList = availableList.stream().filter(car -> car.getId() != id).collect(Collectors.toList());
        }

        return availableList;
    }

    private List<Integer> getRentedCarsId() {
        List<Integer> rentedCarsId = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT rented_car_id FROM customer WHERE rented_car_id IS NOT NULL");
            while (result.next()) {
                rentedCarsId.add(result.getInt("rented_car_id"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieved customers that rented");
            System.out.println(e.getMessage());
        }

        return rentedCarsId;
    }

    public String getName() {
        return name;
    }

    private void updateCars() {
        this.cars = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM car WHERE company_id = " + this.id);
            while (result.next()) {
                cars.add(new Car(result.getInt("id"), result.getString("name"), result.getInt("company_id")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve car data");
            System.out.println(e.getMessage());
        }
    }

    public void optionsMenu() {
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
        updateCars();
        if (this.cars.isEmpty()) {
            System.out.println("\nThe car list is empty!");
        } else {
            System.out.println("\nCar list:");
            for (int i = 0; i < this.cars.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, this.cars.get(i).getName());
            }
        }
    }

    private void createCar() {
        System.out.println("Enter the car name:");
        String name = Main.scanner.nextLine();
        try {
            Main.getStatement().executeUpdate("INSERT INTO car (name, company_id) VALUES ('" + name + "', " + this.id + ")");
            System.out.println("the car was added!");
        } catch (SQLException e) {
            System.out.println("Failed to add new car");
            System.out.println(e.getMessage());
        }
    }

    private void printChooseCarInstructions() {
        System.out.printf("\n'%s' company\n", this.name);
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
