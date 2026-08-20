package com.shopsphere.order.controller;

import com.shopsphere.order.dto.CheckoutRequest;
import com.shopsphere.order.entity.Order;
import com.shopsphere.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //checkout Order
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> checkout(@RequestHeader("X-User-Id") Long userId,@Valid @RequestBody CheckoutRequest request) {
        Order order = orderService.checkout(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    //get the order
    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

    //get All Orders
    @GetMapping()
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public List<Order> getUserOrders(@RequestHeader("X-User-Id") Long userId) {
        return orderService.getOrdersForUser(userId);
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Order updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        System.out.println("Reached for update");
        return orderService.updateStatus(id, body.get("status"));
    }
}
