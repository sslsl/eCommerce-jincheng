package com.echo.Service;

import com.echo.models.CategoryEnum;
import com.echo.models.Product;
import com.echo.models.ProductDTO;
import com.echo.models.ProductStatus;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(String token, Product product) throws LoginException;

    public List<ProductDTO> getAllProductsOfSeller(Integer id);

    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum);

    public List<ProductDTO> getProductsOfStatus(ProductStatus status);

    public Product updateProductQuantityWithId(Integer id,ProductDTO prodDTO);

}
