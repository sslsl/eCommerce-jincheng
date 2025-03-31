package com.echo.Service;

import com.echo.models.Product;
import com.echo.models.ProductDTO;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(String token, Product product) throws LoginException;

    public List<ProductDTO> getAllProductsOfSeller(Integer id);
}
