package com.shopsphere.admin.dto;

import java.io.Serializable;
import java.math.BigDecimal;


public class OrderPlacedEvent implements Serializable {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;

    public OrderPlacedEvent() {}

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
