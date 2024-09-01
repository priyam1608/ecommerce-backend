package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Rating;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating> getProductRatings(Long productId);
}
