package com.shopsphere.catalog.service;

import com.shopsphere.catalog.dto.ProductCreateRequest;
import com.shopsphere.catalog.dto.ProductUpdateRequest;
import com.shopsphere.catalog.dto.StockReductionDto;
import com.shopsphere.catalog.entity.Category;
import com.shopsphere.catalog.entity.Product;
import com.shopsphere.catalog.exception.ApiException;
import com.shopsphere.catalog.repository.CategoryRepository;
import com.shopsphere.catalog.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /** Powers GET /api/catalog/products with optional search/filter/sort. */
    public List<Product> searchProducts(String keyword, Long categoryId,
                                         BigDecimal minPrice, BigDecimal maxPrice,
                                         String sortBy) {
        List<Product> products = productRepository.search(
                (keyword == null || keyword.isBlank()) ? null : keyword,
                categoryId, minPrice, maxPrice);

        if (sortBy != null) {
            Comparator<Product> comparator = switch (sortBy) {
                case "priceAsc" -> Comparator.comparing(Product::getPrice);
                case "priceDesc" -> Comparator.comparing(Product::getPrice).reversed();
                case "nameAsc" -> Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
                default -> null;
            };
            if (comparator != null) {
                products.sort(comparator);
            }
        }
        return products;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found", HttpStatus.NOT_FOUND));
    }

    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product();
        applyCreateRequest(request, product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductUpdateRequest request) {
        Product product = getProduct(id);
        applyUpdateRequest(request, product);
        return productRepository.save(product);
    }

    private void applyCreateRequest(ProductCreateRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());

        // categoryId is @NotNull on ProductCreateRequest, so this guard is a
        // defensive safety net for any programmatic callers that bypass validation.
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ApiException("Category not found", HttpStatus.NOT_FOUND));
            product.setCategory(category);
        }
    }

    private void applyUpdateRequest(ProductUpdateRequest request, Product product) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());

        // categoryId is @NotNull on ProductUpdateRequest, so this guard is a
        // defensive safety net for any programmatic callers that bypass validation.
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new ApiException("Category not found", HttpStatus.NOT_FOUND));
            product.setCategory(category);
        }
    }


    //reduction of stock it will only be called from the order service
    @Transactional
    public void reduceStock(List<StockReductionDto> items){
        for(StockReductionDto item: items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(()-> new RuntimeException("Product not found "+ item.getProductId()));

            if(product.getStockQuantity() < item.getQuantity()){
                throw new RuntimeException("Not enough stock for Product "+ item.getProductId());
            }

            product.setStockQuantity(product.getStockQuantity()-item.getQuantity());
            productRepository.save(product);
        }
    }
}
