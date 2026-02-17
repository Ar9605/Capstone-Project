package com.application.product_service.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.application.product_service.dto.Productdto;
import com.application.product_service.exception.ResourceNotFoundException;
import com.application.product_service.model.Product;
import com.application.product_service.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Productdto productDto) {
        Product product = new Product(
                productDto.getSkuCode(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice()
        );
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Productdto getProductBySku(String skuCode) {
        Product product = productRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + skuCode));
        
        Productdto productDto = new Productdto();
        productDto.setSkuCode(product.getSkuCode());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        
        return productDto;
    }
}