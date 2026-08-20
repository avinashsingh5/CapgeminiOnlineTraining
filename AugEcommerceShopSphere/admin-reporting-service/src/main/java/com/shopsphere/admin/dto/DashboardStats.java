package com.shopsphere.admin.dto;

import java.math.BigDecimal;

public class DashboardStats {
    private long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;

    public DashboardStats(long totalOrders, BigDecimal totalRevenue, BigDecimal averageOrderValue) {
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.averageOrderValue = averageOrderValue;
    }

    public long getTotalOrders() { return totalOrders; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public BigDecimal getAverageOrderValue() { return averageOrderValue; }
}
