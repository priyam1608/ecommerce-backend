package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.CartException;
import com.sharma.EcommerceBackend.Exceptions.CartItemException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.CartItem;
import com.sharma.EcommerceBackend.models.Product;

public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws UserException, CartItemException;

    public CartItem isCartItemExist(Cart cart, Product product,String size,Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserException;
    public CartItem findCartItemById(Long cartItem) throws CartItemException;


}
