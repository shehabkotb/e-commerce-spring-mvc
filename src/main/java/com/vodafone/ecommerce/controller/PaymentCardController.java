package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.model.PaymentCard;
import com.vodafone.ecommerce.service.PaymentCardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PaymentCardController {
    private final PaymentCardService paymentCardService;

    @GetMapping(value = "/webapi/payments")
    public List<PaymentCard> getCardList() {
        return paymentCardService.getCardList();

    }
    @PostMapping(value = "/webapi/payments")
    public String getCardDetails(@RequestBody PaymentDto paymentCard)  {
        return paymentCardService.payFromPaymentCard(paymentCard);
    }

}
