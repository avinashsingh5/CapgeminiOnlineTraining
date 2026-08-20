package com.shopsphere.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/** Message payload published to RabbitMQ when a checkout succeeds.
 *  admin-reporting-service listens for this to log/notify. */
public class OrderPlacedEvent implements Serializable {

    private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;

    public OrderPlacedEvent() {}

    public OrderPlacedEvent(Long orderId, Long userId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
}
