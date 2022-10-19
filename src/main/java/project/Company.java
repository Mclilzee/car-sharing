package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Company {

    private int id;
    private String name;
    private List<Car> cars;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
        this.cars = new ArrayList<>();
    }

    public List<Car> getCars() {
        updateCars();
        return this.cars;
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

    private void updateCars() {
        this.cars = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT id, name FROM car WHERE company_id = " + this.id);
            while (result.next()) {
                cars.add(new Car(result.getInt("id"), result.getString("name")));
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
