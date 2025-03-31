package com.echo.Service;

import com.echo.models.Customer;
import com.echo.models.Seller;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;


public interface SellerService {
    public List<Seller> getAllSellers(String token);
    public Seller addSeller(Seller seller);
    public Seller getCurrentlyLoggedInSeller(String token) throws LoginException;
}
