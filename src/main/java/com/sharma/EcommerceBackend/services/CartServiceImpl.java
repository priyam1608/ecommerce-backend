package com.sharma.EcommerceBackend.services;

import com.sharma.EcommerceBackend.Exceptions.ProductException;
import com.sharma.EcommerceBackend.Exceptions.UserException;
import com.sharma.EcommerceBackend.models.Cart;
import com.sharma.EcommerceBackend.models.CartItem;
import com.sharma.EcommerceBackend.models.Product;
import com.sharma.EcommerceBackend.models.User;
import com.sharma.EcommerceBackend.repositories.CartRepository;
import com.sharma.EcommerceBackend.requests.AddItemRequest;
import com.sharma.EcommerceBackend.serviceInterfaces.CartItemService;
import com.sharma.EcommerceBackend.serviceInterfaces.CartService;
import com.sharma.EcommerceBackend.serviceInterfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    public CartRepository cartRepository;

    @Autowired
    public CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart = cartRepository.findByUserId(userId);
        Product product = productService.findProductById(req.getProductId());

        CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

        if(isPresent==null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);
            cartItem.setPrice(product.getDiscountedPrice()* req.getQuantity());
            cartItem.setSize(req.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Item added to cart";
    }

    @Override
    public Cart findUserCart(Long userId) throws UserException {
        Cart cart = cartRepository.findByUserId(userId);

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem: cart.getCartItems()){
            totalPrice = totalPrice + cartItem.getPrice();
            totalDiscountedPrice = totalDiscountedPrice + cartItem.getDiscountedPrice();
            totalItem = totalItem + cartItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cartRepository.save(cart);
    }
}
