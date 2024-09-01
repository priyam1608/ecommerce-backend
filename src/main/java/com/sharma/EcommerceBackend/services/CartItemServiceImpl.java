package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.CartException;
import com.sharma.EcommerceBackend.Exceptions.CartItemException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.CartItem;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.CartItemRepository;
import com.sharma.EcommerceBackend.repositories.CartRepository;
import com.sharma.EcommerceBackend.serviceInterfaces.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private static final Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());


        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws UserException, CartItemException {
        System.out.println(cartItem.toString());
        CartItem item = findCartItemById(cartItemId);
        Optional<User> user = userService.findUserById(item.getUserId());
        System.out.println(cartItem.getProduct());

        if(user.get().getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getProduct().getPrice()*item.getQuantity());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());

            return cartItemRepository.save(item);
        }
        else {
            throw new UserException("User id passed wrong in the update function");
        }
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExist(cart,product,size,userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        Optional<User> user = userService.findUserById(cartItem.getUserId());

        if(user.get().getId().equals(userId)){
            cartItemRepository.deleteById(cartItemId);
        }
        else {
            throw new UserException("User id passed wrong in the remove function");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItem) throws CartItemException {
        Optional<CartItem> item = cartItemRepository.findById(cartItem);
        if(item.isPresent()){
            return item.get();
        }
        else {
            throw new CartItemException("cartItem not found");
        }
    }
}
