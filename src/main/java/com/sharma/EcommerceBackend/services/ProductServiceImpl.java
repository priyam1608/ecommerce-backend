package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.models.Category;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.repositories.CartItemRepository;
import com.sharma.EcommerceBackend.repositories.CategoryRepository;
import com.sharma.EcommerceBackend.repositories.ProductRepository;
import com.sharma.EcommerceBackend.requests.CreateProductRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if(topLevel == null){
            Category category = new Category();
            category.setName(req.getTopLevelCategory());
            category.setLevel(1);

            topLevel = categoryRepository.save(category);
        }

        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());

        if(secondLevel==null){
            Category category = new Category();
            category.setName(req.getSecondLevelCategory());
            category.setParentCategory(topLevel);
            category.setLevel(2);

            secondLevel = categoryRepository.save(category);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());

        if(thirdLevel==null){
            Category category = new Category();
            category.setName(req.getThirdLevelCategory());
            category.setParentCategory(secondLevel);
            category.setLevel(3);

            thirdLevel = categoryRepository.save(category);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPercent(req.getDiscountPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct= productRepository.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        cartItemRepository.deleteById(productId);
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);

        if(req.getQuantity() != 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            return product.get();
        }

        throw new ProductException("Product Not Found with id - "+id);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);

        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

        if(!colors.isEmpty()){
            products = products.stream().filter(p-> colors.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if(stock!= null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product> pageContent = products.subList(startIndex,endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());

        return filteredProducts;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
