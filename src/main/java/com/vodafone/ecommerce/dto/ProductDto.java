package com.vodafone.ecommerce.dto;

import com.vodafone.ecommerce.enums.Category;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product Should not be Empty")
    @Max(value = 255)
    private String name;
    @NotBlank(message = "Photo Url Should not be Empty")
    @Max(value = 255)
    private String photoUrl;
    @NotBlank(message = "Description Should not be Empty")
    @Max(value = 255)
    private String description;
    @NotNull(message = "Price Should not be Empty")
    private Double price;
    @NotNull(message = "Category name Should not be Null")
    private Category category;
    @Min(value = 1, message = "Min stockQuantity is 1")
    private Long stockQuantity;
}
