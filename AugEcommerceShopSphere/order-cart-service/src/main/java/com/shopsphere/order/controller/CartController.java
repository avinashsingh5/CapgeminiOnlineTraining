package com.shopsphere.order.controller;

import com.shopsphere.order.dto.AddToCartRequest;
import com.shopsphere.order.entity.CartItem;
import com.shopsphere.order.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartItem addToCart(@RequestHeader("X-User-Id") Long userId,@Valid @RequestBody AddToCartRequest request) {

        return cartService.addOrUpdateItem(userId,request);
    }


    @GetMapping()
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<CartItem> getCart(@RequestHeader("X-User-Id") Long userId) {

        return cartService.getCart(userId);
    }


    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> clearCart(@RequestHeader("X-User-Id") Long userId) {

        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart deleted Successfully");
    }
}
