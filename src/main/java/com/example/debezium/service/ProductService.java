package com.example.debezium.service;

import com.example.debezium.entity.Product;

public interface ProductService {
    void handleEvent(String operation, String documentId, Product product);
}
