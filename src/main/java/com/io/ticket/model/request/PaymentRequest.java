package com.io.ticket.model.request;

import lombok.Builder;

@Builder
public record PaymentRequest(
        long amount,
        String factorNumber,
        String redirectUrl,
        int expireInSeconds
) {

    public static PaymentRequest init(long amount,
                                      String factorNumber,
                                      String redirectUrl,
                                      int expireInSeconds){
        return PaymentRequest.builder()
                .amount(amount)
                .factorNumber(factorNumber)
                .redirectUrl(redirectUrl)
                .expireInSeconds(expireInSeconds).build();
    }
}
