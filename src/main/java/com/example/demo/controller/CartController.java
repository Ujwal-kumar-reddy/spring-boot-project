package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {


    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add to cart
    @PostMapping("/add")
    public CartItem addToCart(@RequestParam String userId,
                              @RequestParam String productId,
                              @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    // Get cart
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable String userId) {
        return cartService.getCart(userId);
    }

    // Clear cart
    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}
