package com.vodafone.ecommerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.ecommerce.exception.InsufficientBalanceException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.PaymentCard;
import com.vodafone.ecommerce.service.PaymentCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;
import java.io.DataInput;
import java.io.IOException;
import java.util.Arrays;
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
    public String getCardDetails(@RequestBody PaymentCard paymentCard)  {
        return paymentCardService.getCardDetails(paymentCard);
    }

}
