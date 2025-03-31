package com.echo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.echo.exception.ProductNotFoundException;
import com.echo.models.CartDTO;
import com.echo.models.CartItem;
import com.echo.models.Product;
import com.echo.models.ProductStatus;
import com.echo.repository.ProductDao;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    ProductDao productDao;

    @Override
    public CartItem createItemforCart(CartDTO cartdto) {

        Product existingProduct = productDao.findById(cartdto.getProductId()).orElseThrow( () -> new ProductNotFoundException("Product Not found"));

        if(existingProduct.getStatus().equals(ProductStatus.OUTOFSTOCK) || existingProduct.getQuantity() == 0) {
            existingProduct.setStatus(ProductStatus.OUTOFSTOCK);
            productDao.save(existingProduct);
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }

        CartItem newItem = new CartItem();

        newItem.setCartItemQuantity(cartdto.getQuantity());

        newItem.setCartProduct(existingProduct);

        return newItem;
    }

}

