package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import com.sharma.EcommerceBackend.serviceInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProductsHandler(@RequestParam String category, @RequestParam List<String> colors, @RequestParam List<String> sizes, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){

        Page<Product> res = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
}
