package com.echo.repository;

import com.echo.models.CategoryEnum;
import com.echo.models.Product;
import com.echo.models.ProductDTO;
import com.echo.models.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    @Query("select new com.echo.models.ProductDTO(p.productName, p.manufacturer, p.price, p.quantity) from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getAllProductsOfSeller(@Param("id") Integer id);


    @Query("select new com.echo.models.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.category=:catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);


    @Query("select new com.echo.models.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.status=:status")
    public List<ProductDTO> getProductsWithStatus(@Param("status") ProductStatus status);


}
