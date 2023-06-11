package com.vodafone.ecommerce.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PaymentDto {
    @Max(value = 16, message = "Card number cannot be more that 16 digits")
    private String cardNumber;
    @Max(value = 3, message = "ccv cannot be more that 3 digits")
    private String cvv;
    @Max(value =2 , message = "month cannot be more that 2 digits")
    private String month;
    @Max(value =2 , message = "year cannot be more that 2 digits")
    private String year;
    private Double amount;
}
