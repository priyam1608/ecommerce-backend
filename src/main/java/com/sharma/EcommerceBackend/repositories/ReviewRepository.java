package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("Select r from Review r where r.product.id=:productId")
    public List<Review> getProductRatings(@Param("productId") Long productId);
}
