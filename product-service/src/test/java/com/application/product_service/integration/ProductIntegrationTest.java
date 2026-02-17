package com.application.product_service.integration;



import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.application.product_service.dto.Productdto;
import com.application.product_service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

//Hardcoding H2 database details due to issues with application.properties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "eureka.client.enabled=false",
    "spring.cloud.config.enabled=false",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class ProductIntegrationTest {

    @LocalServerPort
    private int port; // Captures the random port Spring chose

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper; // Spring's JSON helper

    private final HttpClient httpClient = HttpClient.newHttpClient();


    @Test
    public void testCreateProduct_IntegrationFlow() throws Exception {
        // 1. Arrange
        Productdto requestDto = new Productdto();
        requestDto.setSkuCode("INT-FINAL-001");
        requestDto.setName("Integration Masterpiece");
        requestDto.setPrice(BigDecimal.valueOf(2500));
        requestDto.setDescription("This is a test description");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // 2. Act: Send a REAL HTTP Request using standard Java
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/products"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // 3. Assert
        assertEquals(201, response.statusCode(), "Expected a 201 CREATED response");
        assertEquals(1, productRepository.findAll().size(), "Database should have 1 product");
        assertEquals("INT-FINAL-001", productRepository.findAll().get(0).getSkuCode());
    }
}