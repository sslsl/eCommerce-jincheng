package com.echo.repository;

import com.echo.models.Customer;
import com.echo.models.Order;
import com.echo.models.OrderStatusValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.orderStatus = :status WHERE o.orderId = :orderId")
    public int updateOrderStatusById(@Param("orderId") Integer orderId, @Param("status") OrderStatusValues status);

}
