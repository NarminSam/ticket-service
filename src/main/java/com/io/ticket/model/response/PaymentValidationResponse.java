package com.io.ticket.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentValidationResponse extends PaymentBaseResponse{
    private String requestUid;
    private TransferInfo transferInfo;

    @Getter
    @Setter
    public static class TransferInfo {
        private long transferId;
        private String transactionNumber;
        private long utcTimestamp;}
}
