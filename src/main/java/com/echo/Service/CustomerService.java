package com.echo.Service;

import com.echo.models.Customer;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomer(String token);
    public Customer addCustomer(Customer customer);
    public Customer getLoggedInCustomerDetails(String token) throws LoginException;
}
