package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Review;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.ReviewRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.ReviewService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @PostMapping("/create-review")
    public ResponseEntity<?> createReviewHandler(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            Review review = reviewService.createReview(req, user.get());
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductRatingsHandler(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) throws UserException {
        userService.findUserProfileByJwt(jwt);

        List<Review> productReviews = reviewService.getProductReviews(productId);
        return new ResponseEntity<>(productReviews,HttpStatus.OK);
    }
}
