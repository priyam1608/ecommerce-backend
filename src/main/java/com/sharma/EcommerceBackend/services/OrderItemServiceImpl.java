package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.models.OrderItem;
import com.sharma.EcommerceBackend.repositories.OrderItemRepository;
import com.sharma.EcommerceBackend.serviceInterfaces.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
