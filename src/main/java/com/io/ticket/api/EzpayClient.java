package com.io.ticket.api;

import com.io.ticket.model.request.PaymentRequest;
import com.io.ticket.model.response.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payment-api", url = "http://your-server-url.com")
public interface EzpayClient {

    @PostMapping("/api/merchant/payment/ipg")
    PaymentResponse initiatePayment(
            @RequestHeader("Token") String token,
            @RequestBody PaymentRequest paymentRequest);
}
