package com.sharma.EcommerceBackend.serviceInterfaces;

import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findUserById(Long userId) throws UserException;

    public Optional<User> findUserProfileByJwt(String jwt) throws UserException;
}
