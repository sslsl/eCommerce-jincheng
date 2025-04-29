package com.echo.Service;

import com.echo.exception.OrderException;
import com.echo.models.*;
import com.echo.repository.OrderDao;
import com.echo.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao oDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CartServiceImpl cartservice;

    @Autowired
    private ProductDao productDao;


    @Override
    public Order saveOrder(OrderDTO odto, String token) throws LoginException {

        Order newOrder = new Order();
        Customer loggedInCustomer = customerService.getLoggedInCustomerDetails(token);
        if(loggedInCustomer != null){
            newOrder.setCustomer(loggedInCustomer);
            String userCardNumber;
            if(loggedInCustomer.getCreditCard() != null){
                userCardNumber = loggedInCustomer.getCreditCard().getCardNumber();
            }else{
                userCardNumber = odto.getCardNumber().getCardNumber();
                loggedInCustomer.setCreditCard(odto.getCardNumber());
                customerService.addCustomer(loggedInCustomer);
            }

            if(loggedInCustomer.getCustomerCart()!=null) {
                List<CartItem> productInCart = loggedInCustomer.getCustomerCart().getCartItems();
                List<CartItem> productInOrder = new ArrayList<>(productInCart);


                if (productInCart != null && productInCart.size() != 0) {

                    if (userCardNumber.equals(odto.getCardNumber().getCardNumber())) {
                        newOrder.setCardNumber(userCardNumber);
                        newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
                        newOrder.setDate(LocalDate.now());
                        newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
                        newOrder.setOrderCartItems(productInOrder);
                        cartservice.clearCart(token);
                        List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItems();
                        for(CartItem cartItem: cartItemsList){
                            CartDTO cart  = new CartDTO();
                            cart.setProductId(cartItem.getCartProduct().getProductId());
                            Product product = cartItem.getCartProduct();
                            reduceProductQuantity(product,cartItem.getCartItemQuantity());
                            if(product.getQuantity()-1 > 0){
                                System.out.println("deleting product from db");
                                product.setQuantity(product.getQuantity()-1);
                            }else{
                                product.setQuantity(0);
                                product.setStatus(ProductStatus.OUTOFSTOCK);
                            }
                            productDao.save(product);
                        }
                        return oDao.save(newOrder);
                    } else {
                        System.out.println("user card information is different with profile");
                        newOrder.setCardNumber(odto.getCardNumber().getCardNumber());
                        newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
                        newOrder.setDate(LocalDate.now());
                        newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
                        newOrder.setOrderCartItems(productInOrder);
                        cartservice.clearCart(token);
                        return oDao.save(newOrder);
                    }
                } else {
                    System.out.println("Empty shopping cart");
                    newOrder.setCardNumber(odto.getCardNumber().getCardNumber());
                    newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValues.EMPTY_CART);
                    newOrder.setOrderCartItems(productInOrder);
                    return oDao.save(newOrder);
                }
            }else{
                System.out.println("Empty shopping cart");
                newOrder.setCardNumber(odto.getCardNumber().getCardNumber());
                newOrder.setAddress(loggedInCustomer.getAddress().get(odto.getAddressType()));
                newOrder.setDate(LocalDate.now());
                newOrder.setOrderStatus(OrderStatusValues.EMPTY_CART);
                return oDao.save(newOrder);
            }
        }else{
            throw new LoginException("Invalid user token");
        }

    }

    @Override
    public List<Order> getAllOrders() throws OrderException {

        List<Order> orders = oDao.findAll();
        if(orders.size()>0)
            return orders;
        else
            throw new OrderException("No Orders exists on your account");
    }

    @Override
    public Order getOrderByOrderId(Integer OrderId) throws OrderException {
        return oDao.findById(OrderId).orElseThrow(()-> new OrderException("No order exists with given OrderId "+ OrderId));

    }


    private void reduceProductQuantity(Product p,  Integer quantity){
        int curQuantity =  p.getQuantity() - quantity;
        if(curQuantity<=0){
            p.setStatus(ProductStatus.OUTOFSTOCK);
        }
        p.setQuantity(curQuantity);
        productDao.save(p);
    }

    @Override
    public int updateOrderStatus(Integer orderId, OrderStatusValues status){
        return oDao.updateOrderStatusById(orderId,status);
    }
}
