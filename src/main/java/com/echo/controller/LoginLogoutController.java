package com.echo.controller;

import com.echo.Service.CustomerService;
import com.echo.Service.LoginLogoutService;
import com.echo.models.CustomerInputDTO;
import com.echo.models.SellerInputDTO;
import com.echo.models.SessionDTO;
import com.echo.models.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class LoginLogoutController {
    @Autowired
    LoginLogoutService loginLogoutService;

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/login/customer", consumes = "application/json")
    public ResponseEntity<UserSession> loginCustomer(@Valid @RequestBody CustomerInputDTO customerInputDTO) throws LoginException {
        return new ResponseEntity<>(loginLogoutService.loginCustomer(customerInputDTO), HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/logout/customer", consumes = "application/json")
    public ResponseEntity<SessionDTO> logoutCustomer(@Valid @RequestBody CustomerInputDTO customerInputDTO) throws LoginException {
        return new ResponseEntity<>(loginLogoutService.logoutCustomer(customerInputDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/getAllUserSession")
    public ResponseEntity<List<UserSession>> logoutCustomer() throws LoginException {
        return new ResponseEntity<>(loginLogoutService.getAllCurrentLoginUser(), HttpStatus.ACCEPTED);

    }

    @PostMapping(value = "/login/seller", consumes = "application/json")
    public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerInputDTO seller) throws LoginException {
        return new ResponseEntity<>(loginLogoutService.loginSeller(seller), HttpStatus.ACCEPTED);
    }
}
