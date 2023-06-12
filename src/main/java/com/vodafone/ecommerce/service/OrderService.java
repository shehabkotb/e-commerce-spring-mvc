package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrdersByUserId(Long userId);

    OrderDto getOrderById(Long orderId);

    void createOrderAndEmptyCart(Long userId);

}
