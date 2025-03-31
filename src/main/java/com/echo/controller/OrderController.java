package com.echo.controller;

import com.echo.Service.OrderService;
import com.echo.models.Order;
import com.echo.models.OrderDTO;
import com.echo.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderDao oDao;

    @Autowired
    private OrderService orderService;


    @PostMapping("order/place")
    public ResponseEntity<Order> addNewOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader("token") String token) throws LoginException{
        Order returned = orderService.saveOrder(orderDTO, token);
        return new ResponseEntity<Order>(returned, HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        List<Order> listOfAllOrders = orderService.getAllOrders();
        return listOfAllOrders;

    }
}
