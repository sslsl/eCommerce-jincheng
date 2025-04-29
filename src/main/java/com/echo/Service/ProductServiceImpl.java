package com.echo.Service;

import com.echo.exception.ProductNotFound;
import com.echo.exception.ProductNotFoundException;
import com.echo.models.*;
import com.echo.repository.ProductDao;
import com.echo.repository.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ProductDTO> getProductsOfStatus(ProductStatus status) {

        List<ProductDTO> list = productDao.getProductsWithStatus(status);

        if (list.size() > 0) {
            return list;
        } else
            throw new ProductNotFoundException("No products found with given status:" + status);
    }

    @Override
    public List<ProductDTO> getProductsOfCategory(CategoryEnum catenum) {

        List<ProductDTO> list = productDao.getAllProductsInACategory(catenum);
        if (list.size() > 0) {

            return list;
        } else
            throw new ProductNotFoundException("No products found with category:" + catenum);
    }

    @Override
    public Product updateProductQuantityWithId(Integer id,ProductDTO prodDto) {
        Product prod = null;
        Optional<Product> opt = productDao.findById(id);

        if(opt!=null) {
            prod = opt.get();
            prod.setQuantity(prod.getQuantity()+prodDto.getQuantity());
            if(prod.getQuantity()>0) {
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            productDao.save(prod);

        }
        else
            throw new ProductNotFoundException("No product found with this Id");

        return prod;
    }

}
