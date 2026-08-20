package com.shopsphere.admin.controller;

import com.shopsphere.admin.dto.DashboardStats;
import com.shopsphere.admin.entity.OrderRecord;
import com.shopsphere.admin.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public DashboardStats getDashboard() {
        return dashboardService.getDashboardStats();
    }

    @GetMapping("/reports/sales")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRecord> getSalesReport() {
        return dashboardService.getSalesReport();
    }
}
