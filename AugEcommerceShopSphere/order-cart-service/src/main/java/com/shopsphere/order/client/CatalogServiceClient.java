package com.shopsphere.order.client;


import com.shopsphere.order.dto.ProductDto;
import com.shopsphere.order.dto.StockReductionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "catalog-product-service")
public interface CatalogServiceClient {

    @GetMapping("/api/catalog/products/{id}")
    ProductDto getProductById (@PathVariable("id") Long id);

    @PostMapping("/api/catalog/products/reduce-stock")
    void reduceStock(@RequestBody List<StockReductionDto> items);
}
