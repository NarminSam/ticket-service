package com.io.ticket.controller;

import com.io.ticket.model.request.EzpayCallbackRequest;
import com.io.ticket.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiatePayment() {
        String paymentLink = paymentService.initiatePayment().getPaymentLink();
        return ResponseEntity.ok(paymentLink);
    }

    // Endpoint to handle the callback after the payment
    @GetMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestParam String factorNumber,
                                                        @RequestParam String requestUid,
                                                        @RequestParam String processUid,
                                                        @RequestParam int statusCode,
                                                        @RequestParam String statusDesc,
                                                        @RequestParam int amount) {
        EzpayCallbackRequest callbackRequest = new EzpayCallbackRequest(factorNumber, requestUid, processUid, statusCode, statusDesc, amount);
        String message = paymentService.handlePaymentCallback(callbackRequest);
        return ResponseEntity.ok(message);
    }
}
