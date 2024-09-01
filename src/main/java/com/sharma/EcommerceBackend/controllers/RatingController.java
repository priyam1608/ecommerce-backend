package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Rating;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.requests.RatingRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.RatingService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @Autowired
    UserService userService;

    @PostMapping("/create-rating")
    public ResponseEntity<?> createRatingHandler(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            Rating rating = ratingService.createRating(req, user.get());
            return new ResponseEntity<>(rating, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRatingsHandler(@PathVariable Long productId,@RequestHeader("Authorization") String jwt) throws UserException {
        userService.findUserProfileByJwt(jwt);

        List<Rating> productRatings = ratingService.getProductRatings(productId);
        return new ResponseEntity<>(productRatings,HttpStatus.OK);
    }

}
