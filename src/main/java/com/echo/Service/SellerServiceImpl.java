package com.echo.Service;

import com.echo.exception.SellerException;
import com.echo.models.Customer;
import com.echo.models.Seller;
import com.echo.models.UserSession;
import com.echo.repository.SellerDao;
import com.echo.repository.UserSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    SellerDao sellerDao;

    @Autowired
    LoginLogoutService loginLogoutService;

    @Autowired
    UserSessionDao sessionDao;

    @Override
    public List<Seller> getAllSellers(String token) {
        List<Seller> sellerList = new ArrayList<>();
        try {
            sellerList = sellerDao.findAll();
            if(sellerList == null || sellerList.size() == 0){
                System.out.println("No seller found");
            }
        }catch (NullPointerException e){
            System.out.println("No customer found");
        }catch (Exception e){
            System.out.println("Exception here");
        }
        return sellerList;
    }

    @Override
    public Seller addSeller(Seller seller) {
        Optional<Seller> existedSeller = sellerDao.findByMobile(seller.getMobile());
        if(existedSeller.isPresent()) {
            System.out.println("Seller existed");
        }
        sellerDao.save(seller);
        return seller;
    }
    @Override
    public Seller getCurrentlyLoggedInSeller(String token) throws LoginException {

        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginLogoutService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Seller existingSeller=sellerDao.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID"));

        return existingSeller;

    }
}
