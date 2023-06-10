package com.vodafone.ecommerce.model;

import lombok.*;

@Data
public class PaymentCard {
    private String cardNumber;
    private Double amount;
}
