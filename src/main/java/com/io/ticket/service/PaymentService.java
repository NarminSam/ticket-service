package com.io.ticket.service;

import com.io.ticket.api.EzpayClient;
import com.io.ticket.common.PaymentResultCode;
import com.io.ticket.exception.ExceptionResource;
import com.io.ticket.model.request.EzpayCallbackRequest;
import com.io.ticket.model.request.PaymentRequest;
import com.io.ticket.model.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final EzpayClient ezpayClient;

    @Value("${ezpay.token}")
    private String EZPAY_TOKEN;
    @Value("${ezpay.redirect.url}")
    private String EZPAY_REDIRECT_URL;
    @Value("${ezpay.expiration.time}")
    private int EZPAY_EXPIRATION_TIME;
    @Value("${ezpay.ticket.fee}")
    private long EZPAY_TICKET_FEE;

    public PaymentResponse initiatePayment(String factorId) {
        try {
            var paymentRequest = PaymentRequest.init(EZPAY_TICKET_FEE, factorId, EZPAY_REDIRECT_URL, EZPAY_EXPIRATION_TIME);
            return ezpayClient.initiatePayment(EZPAY_TOKEN, paymentRequest);
        }catch(Exception e){
            throw new IllegalArgumentException(ExceptionResource.INVALID_PAYMENT_REQUEST);
        }
    }

    public String handlePaymentCallback(EzpayCallbackRequest callbackRequest) {
        if (callbackRequest.statusCode() == PaymentResultCode.SUCCESS.getCode()) {
            ezpayClient.validatePayment(EZPAY_TOKEN, callbackRequest.processUid(), EZPAY_TICKET_FEE);
            return "Ticket purchase successful for factor number: " + callbackRequest.factorNumber();
        } else {
            return "Ticket purchase failed: " + URLDecoder.decode(callbackRequest.statusDesc(), StandardCharsets.UTF_8);
        }
    }
}
