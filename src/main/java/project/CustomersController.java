package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomersController {

    private static List<Customer> customers;

    private CustomersController() {
    }

    static {
        customers = new ArrayList<>();
    }

    private static void updateCustomers() {
        customers = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM customer");
            while (result.next()) {
                customers.add(new Customer(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve customers");
            System.out.println(e.getMessage());
        }
    }

    public static void manageCustomers() {
        Customer customer = getCustomer();
        if (customer != null) {
            customer.optionsMenu();
        }
    }

    public static Customer getCustomer() {
        updateCustomers();
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return null;
        }

        Customer customer;
        while (true) {
            printCustomerChoosingInstructions();
            String input = Main.scanner.nextLine();
            if ("0".equals(input)) {
                return null;
            }

            customer = findCustomer(input);

            if (customer != null) {
                return customer;
            } else {
                System.out.println("There is no customer with name / id -> " + input);
            }
        }
    }

    private static void printCustomerChoosingInstructions() {
        System.out.println();
        System.out.println("Customer list:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, customers.get(i).getName());
        }
        System.out.println("0. Back");
    }

    public static void createCustomer() {
        System.out.println("\nEnter the customer name:");
        String input = Main.scanner.nextLine();
        try {
            Main.getStatement().executeUpdate("INSERT INTO customer (name) VALUES ('" + input + "')");
            System.out.println("the customer was added!\n");
        } catch (SQLException e) {
            System.out.println("Failed to add customer");
            System.out.println(e.getMessage());
        }
    }

    private static Customer findCustomer(String input) {
        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input) - 1;
            return customers.size() > index ? customers.get(index) : null;
        } else {
            return findCustomerByName(input);
        }
    }

    private static Customer findCustomerByName(String name) {
        for (Customer customer : customers) {
            if (name.equalsIgnoreCase(customer.getName())) {
                return customer;
            }
        }
        return null;
    }
}
