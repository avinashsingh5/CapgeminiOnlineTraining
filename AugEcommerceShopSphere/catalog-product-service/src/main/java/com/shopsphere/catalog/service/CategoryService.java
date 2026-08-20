package com.shopsphere.catalog.service;

import com.shopsphere.catalog.dto.CategoryCreateRequest;
import com.shopsphere.catalog.dto.CategoryUpdateRequest;
import com.shopsphere.catalog.entity.Category;
import com.shopsphere.catalog.exception.ApiException;
import com.shopsphere.catalog.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Category not found with id: " + id,
                                HttpStatus.NOT_FOUND
                        ));
    }

    public Category createCategory(CategoryCreateRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new ApiException(
                    "Category already exists: " + request.getName(),
                    HttpStatus.CONFLICT
            );
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryUpdateRequest request) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Category not found with id: " + id,
                                HttpStatus.NOT_FOUND
                        ));

        if (!existingCategory.getName().equals(request.getName())
                && categoryRepository.existsByName(request.getName())) {

            throw new ApiException(
                    "Category already exists: " + request.getName(),
                    HttpStatus.CONFLICT
            );
        }

        existingCategory.setName(request.getName());
        existingCategory.setDescription(request.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Category not found with id: " + id,
                                HttpStatus.NOT_FOUND
                        ));

        categoryRepository.delete(category);
    }
}