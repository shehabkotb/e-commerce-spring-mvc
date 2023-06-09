package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.ProductDto;
import com.vodafone.ecommerce.model.Product;

public class ProductMapper {
    public static Product mapToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .photoUrl(productDto.getPhotoUrl())
                .price(productDto.getPrice())
                .category(productDto.getCategory())
                .stockQuantity(productDto.getStockQuantity())
                .build();
    }

    public static ProductDto mapToProductDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .photoUrl(product.getPhotoUrl())
                .price(product.getPrice())
                .category(product.getCategory())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
