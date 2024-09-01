package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.CartItemException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.CartItem;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.serviceInterfaces.CartItemService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/cart/cart-items")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserService userService;

    @PutMapping("/{cartItemId}/update")
    public ResponseEntity<?> updateCartItemHandler(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt, @RequestBody CartItem cartItem) throws UserException, CartItemException {
        System.out.println(cartItem.toString());
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if(user.isPresent()){
            CartItem updatedCartItem = cartItemService.updateCartItem(user.get().getId(), cartItemId, cartItem);
            return new ResponseEntity<>(updatedCartItem, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("JWT token error",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{cartItemId}/delete")
    public ResponseEntity<?> deleteCartItemHandler(@PathVariable Long cartItemId,@RequestHeader("Authorization") String jwt) throws UserException, CartItemException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            cartItemService.removeCartItem(user.get().getId(),cartItemId);
            return new ResponseEntity<>("Cart Item deleted",HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("JWT token error",HttpStatus.BAD_REQUEST);
        }
    }
}
