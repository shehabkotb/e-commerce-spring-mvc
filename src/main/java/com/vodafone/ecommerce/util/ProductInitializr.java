package com.vodafone.ecommerce.util;

import com.vodafone.ecommerce.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

public class ProductInitializr {

    public static List<ProductDto> getInitProducts () {
        List<ProductDto> resultList = new ArrayList<>();

        ProductDto item1 = ProductDto.builder()
                .id(1)
                .name("iPhone 13 Pro Max")
                .photoUrl("https://m.media-amazon.com/images/I/61IJBsHm97L._AC_UY218_.jpg")
                .price(920.00)
                .build();

        ProductDto item2 = ProductDto.builder()
                .id(2)
                .name("Apple iPhone XR")
                .photoUrl("https://m.media-amazon.com/images/I/31ILQoX1tSL.__AC_SX300_SY300_QL70_FMwebp_.jpg")
                .price(240.18)
                .build();

        ProductDto item3 = ProductDto.builder()
                .id(3)
                .name("iphone 13, 128GB")
                .photoUrl("https://m.media-amazon.com/images/I/71gm8v4uPBL.__AC_SX300_SY300_QL70_FMwebp_.jpg")
                .price(655.00)
                .build();

        return List.of(item1, item2, item3);

    }
}
