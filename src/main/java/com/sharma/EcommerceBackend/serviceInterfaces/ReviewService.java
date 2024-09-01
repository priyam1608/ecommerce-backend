package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Review;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getProductReviews(Long productId);
}
