package com.shopsphere.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderCartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderCartServiceApplication.class, args);
    }
}
