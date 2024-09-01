package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        Optional<User> user = userService.findUserProfileByJwt(jwt);

        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Jwt Token Error",HttpStatus.BAD_REQUEST);
        }
    }
}
