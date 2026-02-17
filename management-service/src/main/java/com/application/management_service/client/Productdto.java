package com.application.management_service.client;

import java.math.BigDecimal;

public class Productdto {
    private String skuCode;
    private String name;
    private BigDecimal price;


    public String getSkuCode() { return skuCode; }
    public void setSkuCode(String skuCode) { this.skuCode = skuCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
}
