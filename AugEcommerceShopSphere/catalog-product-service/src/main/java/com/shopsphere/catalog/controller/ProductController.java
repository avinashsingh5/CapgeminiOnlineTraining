package com.shopsphere.catalog.controller;

import com.shopsphere.catalog.dto.ProductCreateRequest;
import com.shopsphere.catalog.dto.ProductUpdateRequest;
import com.shopsphere.catalog.dto.StockReductionDto;
import com.shopsphere.catalog.entity.Product;
import com.shopsphere.catalog.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/catalog/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    browse / search / filter / sort products.

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String sortBy) {
        return productService.searchProducts(keyword, categoryId, minPrice, maxPrice, sortBy);
    }

//    view a single product's details

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

//    add a new product to the catalog.
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        Product created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //update product details or stock levels.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(id, request);
    }

    //reduce stock
    @PostMapping("/reduce-stock")
    public ResponseEntity<Void> reduceStock(@RequestBody List<StockReductionDto> items) {
        productService.reduceStock(items);
        return ResponseEntity.ok().build();
    }
}
