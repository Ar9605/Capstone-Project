package com.application.management_service.dto;


import java.math.BigDecimal;

public class ProductAvailabilityResponse {
    private String skuCode;
    private String name;
    private BigDecimal price;
    private Integer quantityAvailable;
    private String status; 

    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantityAvailable() { return quantityAvailable; }
    public void setQuantityAvailable(Integer quantityAvailable) { this.quantityAvailable = quantityAvailable; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

