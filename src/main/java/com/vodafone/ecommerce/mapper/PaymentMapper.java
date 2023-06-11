package com.vodafone.ecommerce.mapper;

import com.vodafone.ecommerce.dto.CartItemDto;
import com.vodafone.ecommerce.dto.PaymentDto;
import com.vodafone.ecommerce.model.CartItem;
import com.vodafone.ecommerce.model.PaymentCard;

import static com.vodafone.ecommerce.mapper.ProductMapper.mapToProductDto;

public class PaymentMapper {
    public static PaymentDto mapToPaymentDto(PaymentCard paymentCard) {
        return PaymentDto.builder()
                .cardNumber(paymentCard.getCardNumber())
                .cvv(paymentCard.getCvv())
                .month(paymentCard.getMonth())
                .year(paymentCard.getYear())
                .amount(paymentCard.getAmount())
                .build();
    }
    public static PaymentCard mapToPayment(PaymentDto paymentDto) {
        return PaymentCard.builder()
                .cardNumber(paymentDto.getCardNumber())
                .cvv(paymentDto.getCvv())
                .month(paymentDto.getMonth())
                .year(paymentDto.getYear())
                .amount(paymentDto.getAmount())
                .build();
    }

}
