package com.example.expensetracker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private Pagination pagination = new Pagination();

    public static class Pagination {
        private int defaultSize;

        public int getDefaultSize() {
            return defaultSize;
        }

        public void setDefaultSize(int defaultSize) {
            this.defaultSize = defaultSize;
        }
    }

    public String getName() {
        return name;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setName(String name) {
        this.name = name;
    }
}
