package com.echo.controller;

import com.echo.Service.SellerService;
import com.echo.models.Customer;
import com.echo.models.CustomerInputDTO;
import com.echo.models.Seller;
import com.echo.models.SellerInputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;


@RestController
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping("/getAllSellers")
    public ResponseEntity<List<Seller>> getAllCustomer(String token){
        List<Seller> customerList = sellerService.getAllSellers(token);
        if(customerList != null && customerList.size()>0){
            return new ResponseEntity<>(customerList, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(customerList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "addSeller", consumes = "application/json")
    public ResponseEntity<Seller> addSeller(@RequestBody @Valid SellerInputDTO seller){
        Seller seller1 = sellerService.addSeller(mapper(seller));
        return new ResponseEntity<>(seller1,HttpStatus.ACCEPTED);
    }


    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHandler(@RequestHeader("token") String token) throws LoginException {

        Seller getSeller = sellerService.getCurrentlyLoggedInSeller(token);

        return new ResponseEntity<Seller>(getSeller, HttpStatus.OK);
    }

    private Seller mapper(SellerInputDTO customerInputValue){
        Seller seller = new Seller();
        seller.setSellerId(customerInputValue.getSellerId());
        seller.setEmailId(customerInputValue.getEmailId());
        seller.setFirstName(customerInputValue.getFirstName());
        seller.setLastName(customerInputValue.getLastName());
        seller.setMobile(customerInputValue.getPhone());
        seller.setPassword(customerInputValue.getPassword());
        return seller;
    }
}
