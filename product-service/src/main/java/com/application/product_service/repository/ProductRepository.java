package com.application.product_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySkuCode(String skuCode);
}