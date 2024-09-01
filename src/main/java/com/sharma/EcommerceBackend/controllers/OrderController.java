package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.OrderException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Address;
import com.sharma.EcommerceBackend.models.Order;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.serviceInterfaces.OrderService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrderHandler(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);
        if(user.isPresent()){
            Order order = orderService.createOrder(user.get(), shippingAddress);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order-history")
    public ResponseEntity<?> usersOrderHistoryHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            List<Order> orders = orderService.usersOrderHistory(user.get().getId());
            return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> findOrderByIdHandler(@PathVariable Long orderId,@RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            Order order = orderService.findOrderById(orderId);
            return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }
}
