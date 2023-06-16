package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.OrderDto;
import com.vodafone.ecommerce.dto.OrderItemDto;
import com.vodafone.ecommerce.model.Order;
import com.vodafone.ecommerce.model.OrderItem;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        return OrderDto.builder()
                .createdAt(order.getCreatedAt().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")))
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .orderItemList(order.getOrderItems().stream().map(OrderMapper::mapToOrderItemDto).collect(Collectors.toList()))
                .build();
    }

    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .product(orderItem.getProduct())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getQuantity() * orderItem.getProduct().getPrice())
                .build();
    }
}
