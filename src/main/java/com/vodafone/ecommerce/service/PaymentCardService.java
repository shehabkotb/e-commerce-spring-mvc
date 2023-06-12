package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.PaymentDto;

public interface PaymentCardService {
    String payFromPaymentCard(PaymentDto paymentCard);
}
