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

    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    @Value("${payment.token}")
    private String paymentToken;

    public PaymentResponse initiatePayment() {
        var paymentRequest = PaymentRequest.init(10000, "123456", "http://localhost:8081/", "09111111111", 100000);

        return ezpayClient.initiatePayment(paymentToken, paymentRequest);
    }

    // Method to handle the payment callback
    public String handlePaymentCallback(EzpayCallbackRequest callbackRequest) {
        // Check status code and inform user of success/failure
        if (callbackRequest.statusCode() == 0) {
            return "Ticket purchase successful for factor number: " + callbackRequest.factorNumber();
        } else {
            return "Ticket purchase failed: " + URLDecoder.decode(callbackRequest.statusDesc(), StandardCharsets.UTF_8);
        }
    }
}
