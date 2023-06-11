package com.vodafone.ecommerce.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCard {
    private String cardNumber;
    private Double amount;
    private String cvv;
    private String month;
    private String year;

}
