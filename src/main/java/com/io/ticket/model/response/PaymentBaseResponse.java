package com.io.ticket.model.response;

import com.io.ticket.common.PaymentResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentBaseResponse {
    private PaymentResultCode resultCode;
    private String resultCodeDesc;
    private List<ResultDetail> details;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResultDetail {
        private String field;
        private String message;

    }

}

