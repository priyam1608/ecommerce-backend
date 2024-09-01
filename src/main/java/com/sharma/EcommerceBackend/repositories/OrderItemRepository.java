package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
