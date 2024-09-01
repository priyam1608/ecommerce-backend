package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.CartItem;
import com.sharma.EcommerceBackend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("Select ci from CartItem ci where ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product")Product product,@Param("size") String size, @Param("userId") Long userId);
}
