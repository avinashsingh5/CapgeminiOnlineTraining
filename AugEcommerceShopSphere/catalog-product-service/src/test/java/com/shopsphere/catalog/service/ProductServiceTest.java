package com.shopsphere.catalog.service;

import com.shopsphere.catalog.dto.ProductCreateRequest;
import com.shopsphere.catalog.entity.Product;
import com.shopsphere.catalog.exception.ApiException;
import com.shopsphere.catalog.repository.CategoryRepository;
import com.shopsphere.catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private ProductCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new ProductCreateRequest();
        request.setName("Running Shoes");
        request.setPrice(new BigDecimal("59.99"));
        request.setStockQuantity(20);
    }

    @Test
    void createProduct_shouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

        Product created = productService.createProduct(request);

        assertEquals("Running Shoes", created.getName());
        assertEquals(new BigDecimal("59.99"), created.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProduct_shouldThrow_whenNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ApiException.class, () -> productService.getProduct(99L));
    }
}
