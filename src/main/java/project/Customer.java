package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final int id;
    private final String name;
    private Company chosenCompany;
    private List<Car> cars;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.chosenCompany = null;
        this.cars = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
                    returnCar();
                    break;
                case "3":
                    printCurrentRentedCar();
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
        if (getCustomerRentedCar() != null) {
            System.out.println("\nYou've already rented a car!");
            return;
        }

        chosenCompany = CompaniesController.chooseCompany();
        if (chosenCompany != null) {
            chooseCar();
        }
    }

    private Car getCustomerRentedCar() {
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT rented_car_id FROM customer WHERE id = " + this.id);
            result.first();
            int carId = result.getInt("rented_car_id");
            return carId == 0 ? null : getRentedCar(carId);
        } catch (SQLException e) {
            System.out.println("Failed to retrieve rented car information");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private Car getRentedCar(int carId) {
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM car WHERE id = " + carId);
            result.first();
            return new Car(result.getInt("id"), result.getString("name"), result.getInt("company_id"));
        } catch (SQLException e) {
            System.out.println("Failed to retrieve car using id");
            System.out.println(e.getMessage());
            return null;
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
                break;
            } else {
                System.out.println("There are no cars with name / id -> " + input);
            }
        }
    }

    private void rentSpecificCar(Car car) {
        try {
            Main.getStatement().executeUpdate("UPDATE customer SET rented_car_id = " + car.getId());
            System.out.printf("\nYou rented '%s'\n", car.getName());
        } catch (SQLException e) {
            System.out.println("Failed to rent a car");
            System.out.println(e.getMessage());
        }
    }

    private void returnCar() {
        Car car = getCustomerRentedCar();
        if (car == null) {
            System.out.println("\nYou didn't rent a car!");
            return;
        }

        try {
            Main.getStatement().executeUpdate("UPDATE customer SET rented_car_id = NULL WHERE id = " + this.id);
            System.out.println("\nYou've returned a rented car!");
        } catch (SQLException e) {
            System.out.println("Failed to return a car");
            System.out.println(e.getMessage());
        }
    }

    private void printCurrentRentedCar() {
        Car car = getCustomerRentedCar();
        if (car == null) {
            System.out.println("\nYou didn't rent a car!");
            return;
        }

        String carCompanyName = getCarCompanyName(car);
        System.out.printf("\nYour rented car:\n%s\nCompany:\n%s\n", car.getName(), carCompanyName);
    }

    private String getCarCompanyName(Car car) {
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT name FROM company WHERE id = " + car.getCompanyId());
            result.first();
            return result.getString("name");
        } catch (SQLException e) {
            System.out.println("Failed to retrieve company name");
            System.out.println(e.getMessage());
            return "";
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
