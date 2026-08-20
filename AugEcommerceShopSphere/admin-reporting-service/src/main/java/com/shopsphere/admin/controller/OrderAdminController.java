package com.shopsphere.admin.controller;

import com.shopsphere.admin.dto.UpdateStatusRequest;
import com.shopsphere.admin.service.OrderStatusService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
public class OrderAdminController {

    private final OrderStatusService orderStatusService;

    public OrderAdminController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    //Admin updates order status: Packed / Shipped / Delivered / Cancelled

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id,
                                                   @Valid @RequestBody UpdateStatusRequest request,
                                                   HttpServletRequest httpRequest) {
        System.out.println("Reached the controller");
        orderStatusService.updateOrderStatus(id, request.getStatus(), httpRequest);
        return ResponseEntity.ok().build();
    }
}
