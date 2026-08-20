package com.shopsphere.admin.service;

import com.shopsphere.admin.config.OrderServiceClient;
import com.shopsphere.admin.exception.ApiException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * admin-reporting-service does not own order data (order-cart-service does)
 * so i called order-cart-service internal  PUT /api/orders/{id}/status endpoint
 */
@Service
public class OrderStatusService {

    private final OrderServiceClient orderServiceClient;

    public OrderStatusService(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    public void updateOrderStatus(Long orderId, String status, HttpServletRequest incomingRequest) {
        String token = incomingRequest.getHeader("Authorization");
        String userId = incomingRequest.getHeader("X-User-Id");
        String userRole = incomingRequest.getHeader("X-User-Role");

        try{
            orderServiceClient.updateOrderStatus(orderId,Map.of("status",status),token,userId,userRole);
        }
        catch (FeignException.NotFound e) {

            throw new ApiException("Order not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new ApiException("Failed to update order status: " + e.getMessage(), HttpStatus.BAD_GATEWAY);
        }
    }
}
