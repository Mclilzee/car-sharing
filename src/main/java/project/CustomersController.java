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
}
