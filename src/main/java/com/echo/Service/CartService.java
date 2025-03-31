package com.echo.Service;

import com.echo.exception.CartItemNotFound;
import com.echo.exception.ProductNotFound;
import com.echo.models.Cart;
import com.echo.models.CartDTO;

import javax.security.auth.login.LoginException;

public interface CartService {
    public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound, LoginException;
    public Cart getCartProduct(String token) throws LoginException;
    public Cart removeProductFromCart(CartDTO cartDto,String token) throws ProductNotFound, LoginException;
    public Cart clearCart(String token) throws LoginException;
}
