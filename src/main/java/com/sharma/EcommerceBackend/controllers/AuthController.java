package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.AuthResponse;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.requests.LoginRequest;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.UserRepository;
import com.sharma.EcommerceBackend.serviceInterfaces.CartService;
import com.sharma.EcommerceBackend.services.UserDetailsServiceImpl;
import com.sharma.EcommerceBackend.utils.JwtIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private JwtIUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CartService cartService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Working fine", HttpStatus.CREATED);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> gettingSignUp(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        Optional<User> isEmailExist = userRepo.findByEmail(email);

        if (isEmailExist.isPresent()) {
            throw new UserException("Email is already used with another account");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);

        User savedUser = userRepo.save(newUser);
        cartService.createCart(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> gettingSignIn(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Signin Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username...");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
