package com.vodafone.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private long id;
    private String name;
    private String photoUrl;
    private String description;
    private double price;
}
