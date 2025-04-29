package com.echo.Service;

import com.echo.exception.OrderException;
import com.echo.models.Customer;
import com.echo.models.Order;
import com.echo.models.OrderDTO;
import com.echo.models.OrderStatusValues;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    public Order saveOrder(OrderDTO odto, String token) throws LoginException;

    public Order getOrderByOrderId(Integer OrderId);

    public List<Order> getAllOrders() throws OrderException;

    public int updateOrderStatus(Integer orderId, OrderStatusValues status);
}
