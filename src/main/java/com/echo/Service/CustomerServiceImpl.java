package com.echo.Service;

import com.echo.exception.CustomerNotFound;
import com.echo.models.Customer;
import com.echo.models.UserSession;
import com.echo.repository.CustomerDao;
import com.echo.repository.UserSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerDao customerDao;

    @Autowired
    LoginLogoutService loginLogoutService;

    @Autowired
    UserSessionDao userSessionDao;

    @Override
    public List<Customer> getAllCustomer(String token){

        List<Customer> customerList = new ArrayList<>();
        try {
            customerList = customerDao.findAll();
            if(customerList == null || customerList.size() == 0){
                throw new CustomerNotFound("Customer does not exist");
            }
        }catch (NullPointerException e){
            System.out.println("No customer found");
        }catch (Exception e){
            System.out.println("Exception here");
        }
        return customerList;
    }

    @Override
    public Customer addCustomer(Customer customer){

        Optional<Customer> existedCustomer = customerDao.findByPhone(customer.getPhone());
        if(existedCustomer.isPresent()) {
            System.out.println("Customer existed");
        }
        customerDao.save(customer);
        return customer;
    }

    @Override
    public Customer getLoggedInCustomerDetails(String token) throws LoginException {

//        if(token.contains("customer") == false) {
//            throw new LoginException("Invalid session token for customer");
//        }

        loginLogoutService.checkTokenStatus(token);

        UserSession user = userSessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFound("Customer does not exist");

        Customer existingCustomer = opt.get();

        return existingCustomer;
    }

}
