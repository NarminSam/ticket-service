package com.io.ticket.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse extends PaymentBaseResponse{
        private String paymentLink;
        private String requestUid;
}
