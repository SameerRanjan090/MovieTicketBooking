package dao;

import database.Database;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void addCustomer(Customer customer) {

        String sql = """
                INSERT INTO customers (name, email, phone)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());

            statement.executeUpdate();

            System.out.println("Customer added successfully!");

        } catch (SQLException e) {

            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("A customer with this email already exists.");
            } else {
                System.out.println("Failed to add customer.");
                e.printStackTrace();
            }
        }
    }

    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                );

                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve customers.");
            e.printStackTrace();
        }

        return customers;
    }

    public Customer getCustomerById(int id) {

        String sql = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Customer(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Failed to find customer.");
            e.printStackTrace();
        }

        return null;
    }

    public void updateCustomer(Customer customer) {

        String sql = """
                UPDATE customers
                SET name = ?, email = ?, phone = ?
                WHERE id = ?
                """;

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setInt(4, customer.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to update customer.");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int id) {

        String sql = "DELETE FROM customers WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully!");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            System.out.println("Failed to delete customer.");
            e.printStackTrace();
        }
    }
}