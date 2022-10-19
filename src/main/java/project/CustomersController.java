package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersController {

    private List<Customer> customers;

    public CustomersController() {
        this.customers = new ArrayList<>();
    }

    private void updateCustomers() {
        this.customers = new ArrayList<>();
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

    public void chooseCustomer() {
        updateCustomers();
        if (this.customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return;
        }
        Customer customer = null;
        while (true) {
            printCustomerChoosingInstructions();
            String input = Main.scanner.nextLine();
            if ("0".equals(input)) {
                return;
            }

            customer = getCustomer(input);

            if (customer != null) {
                break;
            } else {
                System.out.println("There is no customer with name / id -> " + input);
            }
        }

        customer.optionsMenu();
    }

    private void printCustomerChoosingInstructions() {
        System.out.println();
        System.out.println("Customer list:");
        customers.forEach(customer -> System.out.printf("%d. %s\n", customer.getId(), customer.getName()));
        System.out.println("0. Back");
    }

    public void createCustomer() {
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

    private Customer getCustomer(String input) {
        if (input.matches("\\d+")) {
            int index = Integer.parseInt(input) - 1;
            return this.customers.size() > index ? this.customers.get(index) : null;
        } else {
            return findCustomerByName(input);
        }
    }

    private Customer findCustomerByName(String name) {
        for (Customer customer : this.customers) {
            if (name.equalsIgnoreCase(customer.getName())) {
                return customer;
            }
        }
        return null;
    }
}
