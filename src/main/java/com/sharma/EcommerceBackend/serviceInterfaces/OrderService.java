package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.OrderException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Address;
import com.sharma.EcommerceBackend.models.Order;
import com.sharma.EcommerceBackend.models.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Address shippingAddress) throws UserException;
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> usersOrderHistory(Long userId);
    public Order placedOrder(Long orderId) throws OrderException;
    public Order confirmedOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order cancelledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders();
    public String deleteOrder(Long orderId) throws OrderException;
}
