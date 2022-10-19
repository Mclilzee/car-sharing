package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
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
    }
}
