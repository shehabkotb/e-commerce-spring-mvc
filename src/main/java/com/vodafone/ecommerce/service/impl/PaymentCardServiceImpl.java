package com.vodafone.ecommerce.service.impl;

import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.exception.InsufficientBalanceException;
import com.vodafone.ecommerce.exception.InvalidCardException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.mapper.PaymentMapper;
import com.vodafone.ecommerce.model.PaymentCard;
import com.vodafone.ecommerce.service.PaymentCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
@AllArgsConstructor

public class PaymentCardServiceImpl implements PaymentCardService {
    private final RestTemplate restTemplate;

    @Override
    public List<PaymentCard> getCardList() {
        String uri="http://localhost:8080/vending_machine/webapi/payments";
        PaymentCard[] paymentCard = restTemplate.getForObject(uri, PaymentCard[].class);
        System.out.println(paymentCard.length);
        return List.of(paymentCard);
    }

    @Override
    public String payFromPaymentCard(PaymentDto paymentDto) {
//        PaymentCard paymentCard= PaymentMapper.mapToPayment(paymentDto);
        String uri="http://localhost:8080/vending_machine/webapi/payments";
        HttpEntity<PaymentDto> request=new HttpEntity<>(paymentDto);
        PaymentDto paymentCard1 = null;
        try {
             paymentCard1 = restTemplate.postForObject(uri, request, PaymentDto.class);
            System.out.println(paymentCard1);

        }catch (HttpClientErrorException exception){
            System.out.println(exception.getRawStatusCode());
            if (exception.getRawStatusCode()==404){
                throw new NotFoundException("The Card You Enter Not Found") ;

            }
            if (exception.getRawStatusCode()==400){
                throw new InsufficientBalanceException("The Card doesn't have enough balance") ;

            } if (exception.getRawStatusCode()==403){
                throw new InvalidCardException("The Card is Invalid") ;

            }



        }
        return "Pay Successfully";
    }
}
