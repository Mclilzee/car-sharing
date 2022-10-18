package project;

import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Scanner;

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

    public void chooseCar(Statement statement, Scanner scanner) {

    }

    private void printChooseCarInstructions() {
        System.out.printf("'%s' company\n", this.name);
        System.out.println("1. Car list");
        System.out.println("2. Create a car");
        System.out.println("0. Back");
    }
}
