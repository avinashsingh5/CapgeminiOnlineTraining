package com.shopsphere.admin.service;

import com.shopsphere.admin.dto.DashboardStats;
import com.shopsphere.admin.entity.OrderRecord;
import com.shopsphere.admin.repository.OrderRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DashboardService {

    private final OrderRecordRepository orderRecordRepository;

    public DashboardService(OrderRecordRepository orderRecordRepository) {
        this.orderRecordRepository = orderRecordRepository;
    }

    //Fetches data from order record which is build by data send by Order service
    public DashboardStats getDashboardStats() {
        List<OrderRecord> records = orderRecordRepository.findAll();

        long totalOrders = records.size();
        BigDecimal totalRevenue = records.stream()
                .map(OrderRecord::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderValue = totalOrders == 0
                ? BigDecimal.ZERO
                : totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, RoundingMode.HALF_UP);

        return new DashboardStats(totalOrders, totalRevenue, averageOrderValue);
    }


    public List<OrderRecord> getSalesReport() {
        return orderRecordRepository.findAll();
    }
}
