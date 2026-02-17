package com.application.product_service.repository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.application.product_service.model.Product;

@SpringBootTest(properties = {
    "spring.config.import=", // <-- THE MAGIC BULLET! Overrides the config server import
    "eureka.client.enabled=false",
    "spring.cloud.config.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveAndFindBySkuCode() {
        Product product = new Product();
        product.setSkuCode("REPO-TEST-001");
        product.setName("Repository Laptop");
        product.setPrice(BigDecimal.valueOf(1200));

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findBySkuCode("REPO-TEST-001");

        assertTrue(foundProduct.isPresent(), "Product should be found in the database");
        assertEquals("Repository Laptop", foundProduct.get().getName());
    }
}