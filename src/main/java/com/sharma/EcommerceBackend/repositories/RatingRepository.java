package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    @Query("Select r from Rating r where r.product.id=:productId")
    public List<Rating> getProductRatings(@Param("productId") Long productId);
}
