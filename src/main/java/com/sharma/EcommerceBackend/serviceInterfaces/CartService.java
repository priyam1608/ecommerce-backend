package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.AddItemRequest;

public interface CartService {

    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId) throws UserException;
}
