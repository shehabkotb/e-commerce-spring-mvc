package com.vodafone.service;

import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.exception.InsufficientBalanceException;
import com.vodafone.ecommerce.exception.InvalidCardException;
import com.vodafone.ecommerce.exception.NotFoundException;
import com.vodafone.ecommerce.model.PaymentCard;
import com.vodafone.ecommerce.service.CartItemSerivce;
import com.vodafone.ecommerce.service.PaymentCardService;
import com.vodafone.ecommerce.service.impl.PaymentCardServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.when;


@SpringBootTest(classes = PaymentCardService.class)

 class PaymentCardServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PaymentCardServiceImpl paymentCardService;


    @Test
    void payFromPaymentCardTest_payFromPaymentCard_returnPaySuccess(){
        //Arrange
        PaymentCard paymentCard= PaymentCard.builder()
                .cardNumber("1234567890123456")
                .month("07")
                .year("23")
                .cvv("123")
                .amount(100.0)
                .build();
        String uri = "http://localhost:8080/vending_machine/webapi/payments";
        PaymentDto paymentDto= PaymentDto.builder()
                .cardNumber("1234567890123456")
                .month("07")
                .year("23")
                .cvv("123")
                .amount(100.0)
                .build();
        //Act
        when(restTemplate.postForObject(uri, new HttpEntity<>(paymentDto), PaymentDto.class)).
                thenReturn(paymentDto);
        String result = paymentCardService.payFromPaymentCard(paymentDto);
        //Assert
        assertEquals("Pay Successfully", result);
        verify(restTemplate).postForObject(uri, new HttpEntity<>(paymentDto), PaymentDto.class);

    }
    @Test

    void payFromPaymentCardTest_payFromPaymentCardWithInvalidCardDetails_throwNotFoundException() {
        //Arrange
        String uri = "http://localhost:8080/vending_machine/webapi/payments";
        PaymentDto paymentDto= PaymentDto.builder()
                .cardNumber("1234567890123456")
                .month("07")
                .year("23")
                .cvv("123")
                .amount(100.0)
                .build();
        //Act
        when(restTemplate.postForObject(uri, new HttpEntity<>(paymentDto), PaymentDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        //Assert
        assertThrows(NotFoundException.class,()->paymentCardService.payFromPaymentCard(paymentDto));

    }
    @Test
    void payFromPaymentCardTest_payFromPaymentCardWithoutEnoughBalance_trowInsufficientBalanceException() {
        //Arrange
        String uri = "http://localhost:8080/vending_machine/webapi/payments";
        PaymentDto paymentDto= PaymentDto.builder()
                .cardNumber("1234567890123456")
                .month("07")
                .year("23")
                .cvv("123")
                .amount(100.0)
                .build();
        //Act
        when(restTemplate.postForObject(uri, new HttpEntity<>(paymentDto), PaymentDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        //Assert
        assertThrows(InsufficientBalanceException.class,()->paymentCardService.payFromPaymentCard(paymentDto));

    }
    @Test
    void payFromPaymentCardTest_payFromPaymentCardInvalidDetails_throwInvalidCardException() {
        //Arrange
        String uri = "http://localhost:8080/vending_machine/webapi/payments";
        PaymentDto paymentDto= PaymentDto.builder()
                .cardNumber("123456789022123456")
                .month("07")
                .year("20")
                .cvv("123")
                .amount(100.0)
                .build();
        //Act
        when(restTemplate.postForObject(uri, new HttpEntity<>(paymentDto), PaymentDto.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));
        //Assert
        assertThrows(InvalidCardException.class,()->paymentCardService.payFromPaymentCard(paymentDto));

    }

}
