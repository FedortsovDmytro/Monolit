package com.example.expensetracker.finance.controller;

import com.example.expensetracker.finance.dto.CategoryResponse;
import com.example.expensetracker.finance.service.AppDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final AppDAO service;

    public CategoryController(AppDAO service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return service.getAllCategories().stream().map(c->{
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(c.getId());
            categoryResponse.setName(c.getCategoryName());
            categoryResponse.setType(c.getCategoryType().name());
            return categoryResponse;
        }).toList();
    }
}
