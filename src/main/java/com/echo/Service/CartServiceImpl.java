package com.echo.Service;

import com.echo.exception.CartItemNotFound;
import com.echo.exception.CustomerNotFound;
import com.echo.exception.ProductNotFound;
import com.echo.models.*;
import com.echo.repository.CartDao;
import com.echo.repository.CustomerDao;
import com.echo.repository.ProductDao;
import com.echo.repository.UserSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartDao cartDao;

    @Autowired
    UserSessionDao userSessionDao;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    LoginLogoutService loginLogoutService;

    @Autowired
    private CartItemService cartItemService;


    @Override
    public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound, LoginException {
        if(token.contains("customer") == false){
            throw new LoginException("Invalid customer token");
        }
        loginLogoutService.checkTokenStatus(token);

        UserSession user = userSessionDao.findByToken(token).get();
        Optional<Customer> tmpCustomer = customerDao.findById(user.getUserId());

        if(tmpCustomer.isEmpty()){
            throw new CustomerNotFound("Customer does not exist");
        }

        Customer customer = tmpCustomer.get();

        Cart customerCart = customer.getCustomerCart();

        CartItem item = cartItemService.createItemforCart(cart);
        Double price = item.getCartProduct().getPrice() * cart.getQuantity();


        if(customerCart == null){
            Cart newCart = new Cart();
            List<CartItem> cartItems = new ArrayList<>();
            cartItems.add(item);
            newCart.setCartItems(cartItems);
            newCart.setCustomer(customer);
            newCart.setCartTotal(price);
            customer.setCustomerCart(newCart);
            customerDao.save(customer);
        }else{
            List<CartItem> cartItems = customer.getCustomerCart().getCartItems();
            if(cartItems.size() == 0){
                cartItems.add(item);
                customerCart.setCartTotal(price);
            }else{
                cartItems.add(item);
                customerCart.setCartTotal(price+customer.getCustomerCart().getCartTotal());
                item.setCartItemQuantity(cart.getQuantity());
            }
            customerCart.setCustomer(customer);
            customerCart.setCartItems(cartItems);
            customer.setCustomerCart(customerCart);

        }
        return cartDao.save(customer.getCustomerCart());
    }

    private CartItem createCartItem(CartDTO cart){
        CartItem cartItem = new CartItem();
        cartItem.setCartItemQuantity(cart.getQuantity());
        Product product = new Product();
        product.setProductName(cart.getProductName());
        cartItem.setCartProduct(product);
        return cartItem;
    }



    @Override
    public Cart getCartProduct(String token) throws LoginException {

        System.out.println("Inside get cart");

        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginLogoutService.checkTokenStatus(token);

        UserSession user = userSessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());


        if(opt.isEmpty())
            throw new CustomerNotFound("Customer does not exist");

        Customer existingCustomer = opt.get();

        Integer cartId = existingCustomer.getCustomerCart().getCartId();


        Optional<Cart> optCart= cartDao.findById(cartId);

        if(optCart.isEmpty()) {
            throw new CartItemNotFound("cart Not found by Id");
        }

        return optCart.get();
    }



    @Override
    public Cart removeProductFromCart(CartDTO cartDto, String token) throws LoginException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginLogoutService.checkTokenStatus(token);

        UserSession user = userSessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFound("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItems();

        if(cartItems.size() == 0) {
            throw new CartItemNotFound("Cart is empty");
        }


        boolean deleteSucceed = false;

        for(CartItem c: cartItems) {
            System.out.println("Item" + c.getCartProduct());
            if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                c.setCartItemQuantity(c.getCartItemQuantity() - 1);

                customerCart.setCartTotal(customerCart.getCartTotal() - c.getCartProduct().getPrice());
                if(c.getCartItemQuantity() == 0) {

                    cartItems.remove(c);


                    return cartDao.save(customerCart);
                }
                deleteSucceed = true;
            }
        }

        if(!deleteSucceed) {
            throw new CartItemNotFound("Product not added to cart");
        }

        if(cartItems.size() == 0) {
            cartDao.save(customerCart);
            throw new CartItemNotFound("Cart is empty now");
        }

        return cartDao.save(customerCart);
    }



    @Override
    public Cart clearCart(String token) throws LoginException {

        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginLogoutService.checkTokenStatus(token);

        UserSession user = userSessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFound("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        if(customerCart.getCartItems().size() == 0) {
            throw new CartItemNotFound("Cart already empty");
        }

        List<CartItem> emptyCart = new ArrayList<>();

        customerCart.setCartItems(emptyCart);

        customerCart.setCartTotal(0.0);

        return cartDao.save(customerCart);
    }
}
