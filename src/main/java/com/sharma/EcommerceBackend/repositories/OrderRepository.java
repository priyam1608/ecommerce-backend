package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("Select o from Orders o where o.user.id=:userId AND (o.orderStatus='Order Placed' OR o.orderStatus='Order Confirmed' OR o.orderStatus='Order Delivered' OR o.orderStatus='Order Shipped' OR o.orderStatus='Order Cancelled')")
    public List<Order> findUsersOrderHistory(@Param("userId") Long userId);
}
