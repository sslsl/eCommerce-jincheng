package com.echo.repository;

import com.echo.models.Product;
import com.echo.models.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("select new com.echo.models.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getAllProductsOfSeller(@Param("id") Integer id);

}
