package com.maersk.shoppingcart.service;

import com.maersk.shoppingcart.entity.ProductEntity;
import com.maersk.shoppingcart.exception.NotAValidProductException;
import com.maersk.shoppingcart.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Page<ProductEntity> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductEntity validateProduct(String productId) {
        UUID product;
        try {
            product = UUID.fromString(productId);
        } catch (Exception e) {
            throw new NotAValidProductException("Please provide a valid Product id");
        }
        return productRepository.findById(product).orElseThrow(() -> new NotAValidProductException("Please provide a valid Product id"));
    }
}
