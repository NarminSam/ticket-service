package com.io.ticket.service;

import com.io.ticket.api.EzpayClient;
import com.io.ticket.common.PaymentResultCode;
import com.io.ticket.exception.ExceptionResource;
import com.io.ticket.exception.MessageResource;
import com.io.ticket.model.request.EzpayCallbackRequest;
import com.io.ticket.model.request.PaymentRequest;
import com.io.ticket.model.response.PaymentResponse;
import com.io.ticket.repo.TicketSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final EzpayClient ezpayClient;
    private final TicketSaleService ticketSaleService;
    private final TicketSaleRepository ticketSaleRepository;

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
        try {
            if (callbackRequest.statusCode() != PaymentResultCode.SUCCESS.getCode()) {
                return handleFailure(callbackRequest);
            }
            var ticketSale = ticketSaleRepository.getTicketSaleByFactor(callbackRequest.factorNumber()).orElseThrow();
            var paymentValidationResponse = ezpayClient.validatePayment(
                    EZPAY_TOKEN,
                    callbackRequest.processUid(),
                    EZPAY_TICKET_FEE
            );

            if (ticketSaleService.isPaymentDuplicate(paymentValidationResponse.getRequestUid())) {
                throw new IllegalArgumentException(ExceptionResource.INVALID_PAYMENT_DETAILS);
            }
            ticketSale.update(callbackRequest.processUid());
            ticketSaleService.saveTicketSale(ticketSale);

            return handleSuccess(callbackRequest);

        } catch (Exception e) {
            return handleFailure(callbackRequest);
        }
    }

    private String handleSuccess(EzpayCallbackRequest callbackRequest) {
        return String.format(MessageResource.TICKET_PURCHASE_SUCCESSFUL, callbackRequest.factorNumber());
    }

    private String handleFailure(EzpayCallbackRequest callbackRequest) {
        return String.format(MessageResource.TICKET_PURCHASE_UNSUCCESSFUL, callbackRequest.factorNumber());
    }
}
