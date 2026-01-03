package com.example.expensetracker.finance.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Category {

    public enum CategoryType {
        EXPENSE,
        INCOME
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;

    private String categoryDescription = "no description";

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public Category() {
    }

    private Category(Builder builder) {
        this.categoryName = builder.categoryName;
        this.categoryType = builder.categoryType;
        this.categoryDescription = builder.categoryDescription;
    }

    public static class Builder {

        private final String categoryName;
        private final CategoryType categoryType;
        private String categoryDescription = "no description";

        public Builder(String categoryName, CategoryType categoryType) {
            this.categoryName = categoryName;
            this.categoryType = categoryType;

        }

        public Builder setCategoryDescription(String categoryDescription) {
            if(categoryDescription!=null){
                this.categoryDescription = categoryDescription;
            }
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }


    public Long getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }



    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + categoryName + '\'' +
                ", type=" + categoryType +
                '}';
    }
}
