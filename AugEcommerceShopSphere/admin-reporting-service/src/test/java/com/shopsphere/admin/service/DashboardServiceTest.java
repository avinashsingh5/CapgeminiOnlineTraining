package com.shopsphere.admin.service;

import com.shopsphere.admin.dto.DashboardStats;
import com.shopsphere.admin.entity.OrderRecord;
import com.shopsphere.admin.repository.OrderRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private OrderRecordRepository orderRecordRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void getDashboardStats_shouldComputeTotalsCorrectly() {
        OrderRecord r1 = new OrderRecord();
        r1.setOrderId(1L);
        r1.setTotalAmount(new BigDecimal("50.00"));

        OrderRecord r2 = new OrderRecord();
        r2.setOrderId(2L);
        r2.setTotalAmount(new BigDecimal("30.00"));

        when(orderRecordRepository.findAll()).thenReturn(List.of(r1, r2));

        DashboardStats stats = dashboardService.getDashboardStats();

        assertEquals(2, stats.getTotalOrders());
        assertEquals(new BigDecimal("80.00"), stats.getTotalRevenue());
        assertEquals(new BigDecimal("40.00"), stats.getAverageOrderValue());
    }

    @Test
    void getDashboardStats_shouldHandleNoOrders() {
        when(orderRecordRepository.findAll()).thenReturn(List.of());

        DashboardStats stats = dashboardService.getDashboardStats();

        assertEquals(0, stats.getTotalOrders());
        assertEquals(BigDecimal.ZERO, stats.getTotalRevenue());
        assertEquals(BigDecimal.ZERO, stats.getAverageOrderValue());
    }
}
