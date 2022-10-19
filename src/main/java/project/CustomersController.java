package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersController {

    private List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet result = Main.getStatement().executeQuery("SELECT * FROM customer");
            while (result.next()) {
                customers.add(new Customer(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve customers");
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public void chooseCustomer() {
        List<Customer> customers = getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("\nThe customer list is empty!\n");
            return;
        }
        printCustomerChoosingInstructions(customers);

    }

    private void printCustomerChoosingInstructions(List<Customer> customers) {
        System.out.println();
        System.out.println("Customer list:");
        customers.forEach(customer -> System.out.printf("%d. %s\n", customer.getId(), customer.getName()));
    }
}
