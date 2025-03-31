package com.echo.Service;

import com.echo.exception.CustomerNotFound;
import com.echo.exception.SellerException;
import com.echo.models.*;
import com.echo.repository.CustomerDao;
import com.echo.repository.SellerDao;
import com.echo.repository.UserSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService{
    @Autowired
    UserSessionDao userSessionDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    SellerDao sellerDao;

    @Override
    public UserSession loginCustomer(CustomerInputDTO customerInputDTO) throws LoginException {
        Optional<Customer> res = customerDao.findByPhone(customerInputDTO.getPhone());
        if(res.isEmpty()){
            throw new CustomerNotFound("Customer Not Found with the given phone number");
        }

        Customer customer = res.get();
        Optional<UserSession> tmp = userSessionDao.findByUserId(customer.getCustomerId());
        if(tmp.isPresent()){
            UserSession userSession = tmp.get();
            if(userSession.getSessionEndTime().isBefore(LocalDateTime.now())){
                userSessionDao.delete(userSession);
            }else{
                throw new LoginException("User already login");
            }
        }else{
            if(customer.getPassword().equals(customerInputDTO.getPassword())){
                UserSession newUserSession = new UserSession();
                newUserSession.setUserId(customer.getCustomerId());
                newUserSession.setUserType("customer");
                newUserSession.setSessionStartTime(LocalDateTime.now());
                newUserSession.setSessionEndTime(LocalDateTime.now().plusHours(1));
                UUID uuid = UUID.randomUUID();
                String token = "customer_"+uuid.toString();
                newUserSession.setToken(token);
                return userSessionDao.save(newUserSession);
            }else{
                throw new LoginException("Incorrect Password. Please retry");
            }
        }

        return null;
    }


    @Override
    public SessionDTO logoutCustomer(CustomerInputDTO customerInputDTO) throws LoginException {
        SessionDTO sessionDTO = new SessionDTO();
        Optional<UserSession> tmp = userSessionDao.findByUserId(customerInputDTO.getCustomerId());
        if(tmp.isEmpty()){
            throw new LoginException("User not logged in");
        }
        UserSession userSession = tmp.get();
        sessionDTO.setToken(userSession.getToken());
        userSessionDao.delete(userSession);

        sessionDTO.setMessage("User Logout successfully");

        return sessionDTO;
    }

    @Override
    public List<UserSession> getAllCurrentLoginUser() {
        return userSessionDao.findAll();
    }

    @Override
    public void checkTokenStatus(String token) throws LoginException {

        Optional<UserSession> opt = userSessionDao.findByToken(token);

        if(opt.isPresent()) {
            UserSession session = opt.get();
            LocalDateTime endTime = session.getSessionEndTime();
            boolean flag = false;
            if(endTime.isBefore(LocalDateTime.now())) {
                userSessionDao.delete(session);
                flag = true;
            }

          //  deleteExpiredTokens();
            if(flag)
                throw new LoginException("Session expired. Login Again");
        }
        else {
            throw new LoginException("User not logged in. Invalid session token. Please login first.");
        }

    }


    // Method to login a valid seller and generate a seller token

    @Override
    public UserSession loginSeller(SellerInputDTO seller) throws LoginException {

        Optional<Seller> res = sellerDao.findByMobile(seller.getPhone());

        if(res.isEmpty())
            throw new SellerException("Seller record does not exist with given mobile number");

        Seller existingSeller = res.get();

        Optional<UserSession> opt = userSessionDao.findByUserId(existingSeller.getSellerId());

        if(opt.isPresent()) {

            UserSession user = opt.get();

            if(user.getSessionEndTime().isBefore(LocalDateTime.now())) {
                userSessionDao.delete(user);
            }
            else
                throw new LoginException("User already logged in");

        }


        if(existingSeller.getPassword().equals(seller.getPassword())) {

            UserSession newSession = new UserSession();

            newSession.setUserId(existingSeller.getSellerId());
            newSession.setUserType("seller");
            newSession.setSessionStartTime(LocalDateTime.now());
            newSession.setSessionEndTime(LocalDateTime.now().plusHours(1));

            UUID uuid = UUID.randomUUID();
            String token = "seller_" + uuid.toString().split("-")[0];

            newSession.setToken(token);

            return userSessionDao.save(newSession);
        }
        else {
            throw new LoginException("Password Incorrect. Try again.");
        }
    }


    // Method to logout a seller and delete his session token

    @Override
    public SessionDTO logoutSeller(SessionDTO session) throws LoginException {

        String token = session.getToken();

        checkTokenStatus(token);

        Optional<UserSession> opt = userSessionDao.findByToken(token);

        if(!opt.isPresent())
            throw new LoginException("User not logged in. Invalid session token. Login Again.");

        UserSession user = opt.get();

        userSessionDao.delete(user);

        session.setMessage("Logged out sucessfully.");

        return session;
    }


    // Method to delete expired tokens

    @Override
    public void deleteExpiredTokens() {

        System.out.println("Inside delete tokens");

        List<UserSession> users = userSessionDao.findAll();

        System.out.println(users);

        if(users.size() > 0) {
            for(UserSession user:users) {
                System.out.println(user.getUserId());
                LocalDateTime endTime = user.getSessionEndTime();
                if(endTime.isBefore(LocalDateTime.now())) {
                    System.out.println(user.getUserId());
                    userSessionDao.delete(user);
                }
            }
        }
    }
}
