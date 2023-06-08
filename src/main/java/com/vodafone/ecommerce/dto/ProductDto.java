package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.enums.Category;
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
    private Category category;
    private long stockQuantity;
}
