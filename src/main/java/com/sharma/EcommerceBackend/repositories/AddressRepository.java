package com.sharma.EcommerceBackend.repositories;

import com.sharma.EcommerceBackend.models.Address;
import com.sharma.EcommerceBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {


}
