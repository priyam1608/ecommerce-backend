package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.models.Rating;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.RatingRepository;
import com.sharma.EcommerceBackend.requests.RatingRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import com.sharma.EcommerceBackend.serviceInterfaces.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ProductService productService;

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        if(product!=null){
            Rating rating = new Rating();
            rating.setUser(user);
            rating.setCreatedAt(LocalDateTime.now());
            rating.setProduct(product);
            rating.setRating(req.getRating());
            return ratingRepository.save(rating);
        }
        else {
            throw new ProductException("Product Not Found");
        }


    }

    @Override
    public List<Rating> getProductRatings(Long productId) {
        return ratingRepository.getProductRatings(productId);
    }
}
