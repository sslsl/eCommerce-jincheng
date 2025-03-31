package com.echo.repository;

import com.echo.models.Customer;
import com.echo.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerDao extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByMobile(String phone);
}
