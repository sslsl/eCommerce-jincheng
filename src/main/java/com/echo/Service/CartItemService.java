package com.echo.Service;

import com.echo.models.CartDTO;
import com.echo.models.CartItem;

public interface CartItemService {
    public CartItem createItemforCart(CartDTO cartdto);
}
