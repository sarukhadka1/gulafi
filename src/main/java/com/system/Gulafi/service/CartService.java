package com.system.Gulafi.service;

import com.system.Gulafi.dto.CartDto;
import com.system.Gulafi.entity.Cart;

import java.util.List;

public interface CartService {

    void add(CartDto cartDto);

    List<Cart> getCartByActiveUser( );

    void delete(int id);

    void edit(CartDto cartDto);
}
