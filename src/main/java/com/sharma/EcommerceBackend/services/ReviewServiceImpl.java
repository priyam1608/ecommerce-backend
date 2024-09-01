package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.models.Review;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.ReviewRepository;
import com.sharma.EcommerceBackend.requests.ReviewRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import com.sharma.EcommerceBackend.serviceInterfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductService productService;

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        if(product!=null){
            Review review = new Review();
            review.setCreatedAt(LocalDateTime.now());
            review.setReview(req.getReview());
            review.setUser(user);
            review.setProduct(product);

            return reviewRepository.save(review);
        }else {
            throw new ProductException("Product Not Found");
        }

    }

    @Override
    public List<Review> getProductReviews(Long productId) {
        return reviewRepository.getProductRatings(productId);
    }
}
