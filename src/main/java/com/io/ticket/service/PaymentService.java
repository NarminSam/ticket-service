package com.io.ticket.service;

import com.io.ticket.api.EzpayClient;
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
        var paymentRequest = PaymentRequest.init(EZPAY_TICKET_FEE, factorId, EZPAY_REDIRECT_URL, "09111111111", EZPAY_EXPIRATION_TIME);
        return ezpayClient.initiatePayment(EZPAY_TOKEN, paymentRequest);
    }

    public String handlePaymentCallback(EzpayCallbackRequest callbackRequest) {
        if (callbackRequest.statusCode() == 0) {
            return "Ticket purchase successful for factor number: " + callbackRequest.factorNumber();
        } else {
            return "Ticket purchase failed: " + URLDecoder.decode(callbackRequest.statusDesc(), StandardCharsets.UTF_8);
        }
    }
}
