package com.echo.repository;

import com.echo.models.Customer;
import com.echo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

//    @Query("select * from Customer c where c.customerId = customerId")
//    public Customer getCustomerByOrderId(int customerId);


}
