//package com.sharma.EcommerceBackend.controllers;
//
//import com.sharma.EcommerceBackend.Exceptions.UserException;
//import com.sharma.EcommerceBackend.models.Address;
//import com.sharma.EcommerceBackend.models.User;
//import com.sharma.EcommerceBackend.repositories.AddressRepository;
//import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api")
//public class AddressController {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    AddressRepository addressRepository;
//
//    @DeleteMapping("/address/{addressId}/delete")
//    public ResponseEntity<?> deleteAddressHandler(@PathVariable Long addressId,@RequestHeader("Authorization") String jwt) throws UserException {
//        Optional<User> user = userService.findUserProfileByJwt(jwt);
//
//        if(user.isPresent()){
//            addressRepository.deleteById(addressId);
//            return new ResponseEntity<>("Address Deleted",HttpStatus.ACCEPTED);
//        }else {
//            return new ResponseEntity<>("User Not Found", HttpStatus.BAD_REQUEST);
//        }
//    }
//}
