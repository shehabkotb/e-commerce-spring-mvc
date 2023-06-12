package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.service.CartService;
import com.vodafone.ecommerce.service.OrderService;
import com.vodafone.ecommerce.service.PaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final PaymentCardService paymentCardService;
    private OrderService orderService;

    @Autowired
    public CheckoutController(CartService cartService, PaymentCardService paymentCardService, OrderService orderService) {
        this.cartService = cartService;
        this.paymentCardService = paymentCardService;
        this.orderService = orderService;
    }

    @GetMapping("/checkout/{userId}")
    public String getCheckoutForm(@ModelAttribute("paymentDto") PaymentDto paymentDto, @PathVariable("userId") Long userId, Model model) {
        List<CartItemDto> cartItemDtoList = cartService.getUserCart(userId);
        double totalPrice = cartService.getCartTotalPrice(userId);

        // Todo show alert to user that cart is empty
        if (cartItemDtoList.isEmpty()) {
            return "redirect:/products";
        }


        model.addAttribute("payment", paymentDto);
        model.addAttribute("cartItems", cartItemDtoList);
        model.addAttribute("totalPrice", totalPrice);
        return "checkout";
    }

    @PostMapping("/checkout/{userId}/pay")
    public String payCart(@ModelAttribute("paymentDto") PaymentDto paymentDto, @PathVariable("userId") Long userId, BindingResult bindingResult, Model model) {
        // call payment service here

        if (bindingResult.hasErrors()) {
            model.addAttribute("PaymentCard", paymentDto);
            return "/checkout";
        }
        double totalPrice = cartService.getCartTotalPrice(userId);
        paymentDto.setAmount(totalPrice);

        paymentCardService.payFromPaymentCard(paymentDto);
        orderService.createOrderAndEmptyCart(userId);

        return "redirect:/user/" + userId + "/orders?success";
    }
}
