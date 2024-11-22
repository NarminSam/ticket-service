package com.io.ticket.model.request;

import lombok.Builder;

@Builder
public record PaymentRequest(
        long amount,
        //long minAmount,
        //long maxAmount,
        String factorNumber,
        String redirectUrl,
        String phoneNumber,
        int expireInSeconds
) {

    public static PaymentRequest init(long amount,
                                      String factorNumber,
                                      String redirectUrl,
                                      String phoneNumber,
                                      int expireInSeconds){
        return PaymentRequest.builder()
                .amount(amount)
                .factorNumber(factorNumber)
                .redirectUrl(redirectUrl)
                .phoneNumber(phoneNumber)
                .expireInSeconds(expireInSeconds).build();
    }
}
