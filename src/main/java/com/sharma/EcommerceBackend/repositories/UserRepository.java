package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);
}
