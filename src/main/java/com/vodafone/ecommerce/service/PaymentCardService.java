package com.vodafone.ecommerce.service;

import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.model.PaymentCard;

import java.util.List;

public interface PaymentCardService {
     List<PaymentCard> getCardList();
    String payFromPaymentCard(PaymentDto paymentCard);
}
