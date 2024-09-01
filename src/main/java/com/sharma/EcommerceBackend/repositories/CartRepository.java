package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("select c from Cart c where c.user.id=:userId")
    public Cart findByUserId(@Param("userId")Long userId);
}
