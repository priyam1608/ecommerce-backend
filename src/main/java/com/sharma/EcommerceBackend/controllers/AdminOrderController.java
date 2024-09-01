package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.OrderException;
import com.sharma.EcommerceBackend.models.Order;
import com.sharma.EcommerceBackend.serviceInterfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/orders")
public class AdminOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrdersHandler(){
        List<Order> allOrders = orderService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @PutMapping("{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.confirmedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("{orderId}/shipped")
    public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("{orderId}/delivered")
    public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @PutMapping("{orderId}/cancelled")
    public ResponseEntity<Order> cancelledOrderHandler(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.cancelledOrder(orderId);
        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{orderId}/delete")
    public ResponseEntity<String> deleteOrderHandler(@PathVariable Long orderId) throws OrderException {
        String res = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}
