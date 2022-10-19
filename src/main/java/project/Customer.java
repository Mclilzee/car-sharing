package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private Car rentedCar;
    private Company chosenCompany;
    private List<Car> cars;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.rentedCar = null;
        this.chosenCompany = null;
        this.cars = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void optionsMenu() {
        boolean quit = false;
        while (!quit) {
            printOptionsMenuInstructions();
            switch (Main.scanner.nextLine()) {
                case "1":
                    rentACar();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("\nThere is no such option, please try again");
                    break;
            }
        }
    }

    private void printOptionsMenuInstructions() {
        System.out.println();
        System.out.println("1. Rent a car");
        System.out.println("2. Return a rented car");
        System.out.println("3. My rented car");
        System.out.println("0. Back");
    }

    private void rentACar() {
        if (rentedCar != null) {
            System.out.println("You've already rented a car!");
            return;
        }

        chosenCompany = CompaniesController.chooseCompany();
        if (chosenCompany != null) {
            chooseCar();
        }
    }

    private void updateCars() {
        if (chosenCompany != null) {
            this.cars = chosenCompany.getCars();
        }
    }

    private void chooseCar() {
        updateCars();
        if (cars.isEmpty()) {
            System.out.printf("\nNo available cars in the '%s' company\n", chosenCompany.getName());
            return;
        }

        Car car;
        while (true) {
            printCars();
            String input = Main.scanner.nextLine();
            if ("0".equals(input)) {
                return;
            }

            car = getCar(input);
            if (car != null) {
                rentSpecificCar(car);
            } else {
                System.out.println("There are no cars with name / id -> " + input);
            }
        }
    }

    private void printCars() {
        System.out.println();
        System.out.println("Choose a car:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, cars.get(i).getName());
        }
    }

    private Car getCar(String input) {
        if (input.matches("^\\d+")) {
            int index = Integer.parseInt(input) - 1;
            return cars.size() > index ? cars.get(index) : null;
        }

        return findCarByName(input);
    }

    private Car findCarByName(String name) {
        for (Car car : cars) {
            if (name.equalsIgnoreCase(car.getName())) {
                return car;
            }
        }

        return null;
    }
}
