package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.OrderDto;
import com.vodafone.ecommerce.model.Order;

import java.time.format.DateTimeFormatter;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order, Integer totalQuantity) {
        return OrderDto.builder()
                .createdAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(totalQuantity)
                .build();
    }
}
