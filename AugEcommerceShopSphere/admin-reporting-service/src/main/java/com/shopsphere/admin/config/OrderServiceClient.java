package com.shopsphere.admin.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "order-cart-service")
public interface OrderServiceClient {

    @PutMapping("/api/orders/{id}/status")
    void updateOrderStatus(@PathVariable("id") Long id,
                           @RequestBody Map<String, String> requestBody,
                           @RequestHeader(value = "Authorization", required = false) String token,
                           @RequestHeader(value = "X-User-Id", required = false) String userId,
                           @RequestHeader(value = "X-User-Role", required = false) String userRole);
}