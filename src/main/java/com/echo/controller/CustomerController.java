package com.echo.controller;

import com.echo.Service.CustomerService;
import com.echo.models.Customer;
import com.echo.models.CustomerInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;



    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> getAllCustomer(String token){
        List<Customer> customerList = customerService.getAllCustomer(token);
        if(customerList != null && customerList.size()>0){
            return new ResponseEntity<>(customerList, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(customerList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "addCustomer", consumes = "application/json")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid CustomerInputDTO customer){
        Customer customer1 = customerService.addCustomer(mapper(customer));
        return new ResponseEntity<>(customer1,HttpStatus.ACCEPTED);
    }

    private Customer mapper(CustomerInputDTO customerInputValue){
        Customer customer = new Customer();
        customer.setCustomerId(customerInputValue.getCustomerId());
        customer.setEmailId(customerInputValue.getEmailId());
        customer.setFirstName(customerInputValue.getFirstName());
        customer.setLastName(customerInputValue.getLastName());
        customer.setPhone(customerInputValue.getPhone());
        customer.setPassword(customerInputValue.getPassword());
        return customer;
    }
}
