package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.AddItemRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.CartService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            Cart userCart = cartService.findUserCart(user.get().getId());
            return new ResponseEntity<>(userCart, HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/add-cart-items")
    public ResponseEntity<?> addCartItemHandler(@RequestBody AddItemRequest req,@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            String res = cartService.addCartItem(user.get().getId(), req);
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }
}
