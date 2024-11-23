package com.io.ticket.api;

import com.io.ticket.model.request.PaymentRequest;
import com.io.ticket.model.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "EzpayClient", url = "${ezpay.gateway.url}")
public interface EzpayClient {

    @PostMapping("/api/merchant/payment/ipg")
    PaymentResponse initiatePayment(
            @RequestHeader("Token") String token,
            @RequestBody PaymentRequest paymentRequest);

    @PutMapping("/api/merchant/payment/ipg/{processUid}")
    Map<String, Object> validatePayment(
            @RequestHeader("Token") String token,
            @PathVariable("processUid") String processUid,
            @RequestParam("amount") Long amount
    );
}
