package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.exception.InsufficientBalanceException;
import com.vodafone.ecommerce.exception.InvalidCardException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.service.PaymentCardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentCardServiceImpl implements PaymentCardService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Override
    public String payFromPaymentCard(PaymentDto paymentDto) {
        HttpEntity<PaymentDto> request = new HttpEntity<>(paymentDto);
        PaymentDto paymentCard1 = null;
        try {
            paymentCard1 = restTemplate.postForObject(paymentServiceUrl, request, PaymentDto.class);
            System.out.println(paymentCard1);

        } catch (HttpClientErrorException exception) {
            System.out.println(exception.getRawStatusCode());
            if (exception.getRawStatusCode() == 404) {
                throw new NotFoundException("The Card You Enter Not Found");

            }
            if (exception.getRawStatusCode() == 400) {
                throw new InsufficientBalanceException("The Card doesn't have enough balance");

            }
            if (exception.getRawStatusCode() == 403) {
                throw new InvalidCardException("The Card is Invalid");
            }
        }
        return "Pay Successfully";
    }
}
