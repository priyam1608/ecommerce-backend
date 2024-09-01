package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.UserRepository;
import com.sharma.EcommerceBackend.utils.JwtIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements com.sharma.EcommerceBackend.serviceInterfaces.UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtIUtil jwtIUtil;

    @Override
    public Optional<User> findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user;
        }
        throw new UserException("User not found with id - "+userId);

    }

    @Override
    public Optional<User> findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtIUtil.getEmailFromToken(jwt);
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            return user;
        }

        throw new UserException("User not found from JWT token - "+jwt);
    }
}
