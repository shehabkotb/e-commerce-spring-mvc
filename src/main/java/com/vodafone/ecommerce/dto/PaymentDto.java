package com.vodafone.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
@Builder
public class PaymentDto {
    @Max(value = 16, message = "Card number cannot be more that 16 digits")
    private String cardNumber;
    @Max(value = 3, message = "ccv cannot be more that 3 digits")
    private String ccv;
    //this can change depending on how it's sent to payment service
    private String date;
}
