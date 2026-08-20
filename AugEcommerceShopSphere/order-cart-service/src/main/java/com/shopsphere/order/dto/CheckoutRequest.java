package com.shopsphere.order.dto;

import jakarta.validation.constraints.NotBlank;

public class CheckoutRequest {

    // Added @NotBlank so they can't submit an empty address
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}