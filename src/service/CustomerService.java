package service;

import dao.CustomerDAO;
import model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService() {
        customerDAO = new CustomerDAO();
    }

    public void addCustomer(String name, String email, String phone) {

        if (name == null || name.isBlank()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            System.out.println("Please enter a valid email.");
            return;
        }

        if (phone == null || phone.isBlank()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }

        Customer customer =
                new Customer(name, email, phone);

        customerDAO.addCustomer(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDAO.getCustomerById(id);
    }

    public void updateCustomer(
            int id,
            String name,
            String email,
            String phone) {

        Customer existingCustomer =
                customerDAO.getCustomerById(id);

        if (existingCustomer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (name == null || name.isBlank()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            System.out.println("Please enter a valid email.");
            return;
        }

        if (phone == null || phone.isBlank()) {
            System.out.println("Phone number cannot be empty.");
            return;
        }

        Customer customer =
                new Customer(id, name, email, phone);

        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {

        Customer customer =
                customerDAO.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        customerDAO.deleteCustomer(id);
    }
}