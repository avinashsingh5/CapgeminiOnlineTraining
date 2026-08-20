package com.shopsphere.catalog.controller;

import com.shopsphere.catalog.dto.CategoryCreateRequest;
import com.shopsphere.catalog.dto.CategoryUpdateRequest;
import com.shopsphere.catalog.entity.Category;
import com.shopsphere.catalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/categories")
public class CategoryController {



    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

//    get all categories
    @GetMapping
    public List<Category> getCategories() {
        System.out.println("Reached controller");
        return categoryService.getAllCategories();
    }


//     get a single category

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }


//      Admin only: create a new category

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {
        System.out.println("Reached catalog contrller ");

        Category created = categoryService.createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

//     Admin only: update a category.

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest request) {

        return categoryService.updateCategory(id, request);
    }


//     Admin only: delete a category.

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}