package com.shopsphere.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateStatusRequest {

    @NotBlank
    @Pattern(regexp = "PACKED|SHIPPED|DELIVERED|CANCELLED",
             message = "status must be one of PACKED, SHIPPED, DELIVERED, CANCELLED")
    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
