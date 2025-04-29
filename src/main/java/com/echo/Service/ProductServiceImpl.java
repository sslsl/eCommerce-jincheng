package com.echo.Service;

import com.echo.exception.ProductNotFound;
import com.echo.models.Product;
import com.echo.models.ProductDTO;
import com.echo.models.Seller;
import com.echo.repository.ProductDao;
import com.echo.repository.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;

    @Autowired
    SellerService sellerService;

    @Autowired
    SellerDao sellerDao;

    @Override
    public Product addProductToCatalog(String token, Product product) throws LoginException {
        Product product1 = productDao.save(product);

        Seller seller = sellerService.getCurrentlyLoggedInSeller(token);

        if(seller != null){
            List<Product> productList = seller.getProduct();
            if(productList != null && productList.size() >0){
                productList.add(product1);
            }else{
                productList = new ArrayList<>();
                productList.add(product1);
            }
            seller.setProduct(productList);
            product1.setSeller(seller);
            sellerDao.save(seller);
//            productDao.save(product1);
        }else{
            throw new LoginException("Seller not found");
        }

        return product1;
    }

    @Override
    public List<ProductDTO> getAllProductsOfSeller(Integer id){
        List<ProductDTO> list = productDao.getAllProductsOfSeller(id);
        if(list != null && list.size()>0){
            return list;
        }else{
            throw new ProductNotFound("No product under the seller");
        }
    }
}
