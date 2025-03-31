package com.echo.repository;

import com.echo.models.Address;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao  extends JpaRepository<Address, Integer> {
}
