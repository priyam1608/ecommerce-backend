package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.OrderException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.*;
import com.sharma.EcommerceBackend.repositories.*;
import com.sharma.EcommerceBackend.serviceInterfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CartService cartService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, Address shippingAddress) throws UserException {

        System.out.println(shippingAddress.toString());
        // Adding and Saving Address
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(shippingAddress);

        // creating orderItems from cartItems
        List<OrderItem> orderItems = new ArrayList<>();
        Cart userCart = cartService.findUserCart(user.getId());
        for (CartItem cartItem :userCart.getCartItems()){

            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSize(cartItem.getSize());
            orderItem.setQuantity(cartItem.getQuantity());

            // save it
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // then finally,creating order
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderItems(orderItems);
        order.setTotalPrice(userCart.getTotalPrice());
        order.setTotalDiscountedPrice(userCart.getTotalDiscountedPrice());
        order.setTotalItem(userCart.getTotalItem());
        order.setDiscount(userCart.getDiscount());
        order.setOrderStatus("Order Pending");
        order.getPaymentDetails().setStatus("Payment Pending");

        Order savedOrder = orderRepository.save(order);

        // Then, manually set "Order" in "orderItem"s model
        for (OrderItem orderItem:orderItems){
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            return order.get();
        }
        else {
            throw new OrderException("Order not found");
        }
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findUsersOrderHistory(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            Order fetchedOrder = order.get();
            fetchedOrder.getPaymentDetails().setStatus("Payment Completed");
            fetchedOrder.setOrderStatus("Order Placed");
            return orderRepository.save(fetchedOrder);
        }
        else {
            throw new OrderException("Order Not Found");
        }
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            Order fetchedOrder = order.get();
                fetchedOrder.setOrderStatus("Order Confirmed");
            return orderRepository.save(fetchedOrder);
        }
        else {
            throw new OrderException("Order Not Found");
        }
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            Order fetchedOrder = order.get();
            fetchedOrder.setOrderStatus("Order Shipped");
            return orderRepository.save(fetchedOrder);
        }
        else {
            throw new OrderException("Order Not Found");
        }
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            Order fetchedOrder = order.get();
            fetchedOrder.setOrderStatus("Order Delivered");
            return orderRepository.save(fetchedOrder);
        }
        else {
            throw new OrderException("Order Not Found");
        }
    }

    @Override
    public Order cancelledOrder(Long orderId) throws OrderException {
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isPresent()){
            Order fetchedOrder = order.get();
            fetchedOrder.setOrderStatus("Order Cancelled");
            return orderRepository.save(fetchedOrder);
        }
        else {
            throw new OrderException("Order Not Found");
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public String deleteOrder(Long orderId) throws OrderException {
        orderRepository.deleteById(orderId);
        return "Your order is deleted";
    }
}
