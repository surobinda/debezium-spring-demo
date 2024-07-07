package com.example.debezium.service;

import com.example.debezium.entity.Product;
import com.example.debezium.repository.ProductRepository;
import io.debezium.data.Envelope;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void handleEvent(String operation, Product product) {

        // Check if the operation is either CREATE or UPDATE
        if (Envelope.Operation.CREATE.code().equals(operation) || Envelope.Operation.UPDATE.code().equals(operation)) {
            // Save the updated product information to the database
            productRepository.save(product);
            // If the operation is DELETE
        } else if (Envelope.Operation.DELETE.code().equals(operation)) {
            // Remove the product from the database using the MongoDB document ID
            //productRepository.removeProductId(product.getId());
            productRepository.deleteById(product.getId());
        }
    }
}
