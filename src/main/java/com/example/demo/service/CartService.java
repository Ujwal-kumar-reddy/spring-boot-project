package com.example.demo.service;

import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // Add item to cart
    public CartItem addToCart(String userId, String productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem existingItem =
                cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            return cartItemRepository.save(existingItem);
        }

        CartItem cartItem = new CartItem();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);

        return cartItemRepository.save(cartItem);
    }

    // Get cart for user
    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    // Clear cart
    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
