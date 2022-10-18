package project;

import java.sql.Statement;

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

    }

    private void createCar() {

    }

    private void printChooseCarInstructions() {
        System.out.printf("'%s' company\n", this.name);
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
