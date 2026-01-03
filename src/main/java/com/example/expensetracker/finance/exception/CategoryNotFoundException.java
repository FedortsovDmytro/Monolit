package com.example.expensetracker.finance.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with id: " + categoryId);
    }
    public CategoryNotFoundException(String message) {
        super("Category not found with id: "+message);
    }

}
