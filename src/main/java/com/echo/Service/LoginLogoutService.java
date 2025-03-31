package com.echo.Service;

import com.echo.models.CustomerInputDTO;
import com.echo.models.SellerInputDTO;
import com.echo.models.SessionDTO;
import com.echo.models.UserSession;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface LoginLogoutService {
    public UserSession loginCustomer(CustomerInputDTO customerInputDTO) throws LoginException;

    public SessionDTO logoutCustomer(CustomerInputDTO customerInputDTO) throws LoginException;

    public List<UserSession> getAllCurrentLoginUser();

    public void checkTokenStatus(String token) throws LoginException;

    public void deleteExpiredTokens();


    public UserSession loginSeller(SellerInputDTO seller) throws LoginException;

    public SessionDTO logoutSeller(SessionDTO session) throws LoginException;


}
