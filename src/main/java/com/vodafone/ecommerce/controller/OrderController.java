package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.OrderDto;
import com.vodafone.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/user/{userId}/orders")
    public String getAllOrders(@PathVariable("userId") Long userId, Model model) {
        List<OrderDto> orderDtoList = orderService.getAllOrdersByUserId(userId);
        model.addAttribute("orderList", orderDtoList);
        return "customer-orders";
    }
}
