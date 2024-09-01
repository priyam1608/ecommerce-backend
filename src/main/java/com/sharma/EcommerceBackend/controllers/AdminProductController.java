package com.sharma.EcommerceBackend.controllers;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.requests.CreateProductRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAllProductsHandler(){
        List<Product> allProducts = productService.findAllProducts();
        return new ResponseEntity<>(allProducts,HttpStatus.ACCEPTED);
    }

    @PostMapping("/create-product")
    public ResponseEntity<Product> createProductHandler(@RequestBody CreateProductRequest req){
        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProductHandler(@PathVariable Long productId, Product req) throws ProductException {
        Product product = productService.updateProduct(productId, req);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<String> deleteProductHandler(@PathVariable Long productId) throws ProductException {
        String res = productService.deleteProduct(productId);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }

    @PostMapping("/create-multiple-products")
    public ResponseEntity<String> createMultipleProductsHandler(@RequestBody CreateProductRequest[] req){

        for (CreateProductRequest product:req){
            productService.createProduct(product);
        }

        return new ResponseEntity<>("ALL PRODUCTS ADDED",HttpStatus.CREATED);
    }

}
