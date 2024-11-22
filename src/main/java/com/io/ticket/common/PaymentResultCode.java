package com.io.ticket.common;

import lombok.Getter;

@Getter
public enum PaymentResultCode {
    SUCCESS(0, "پردازش موفق"),
    FAILED(2, "درخواست یا موجودیت مورد نظر یافت نشد"),
    ERROR(3, "درخواست نامعتبر است"),
    DUPLICATE(4, "درخواست قبلا پردازش شده است"),
    CANCELLED(5, "درخواست غیر مجاز است"),
    INVALID(61, "شماره شبا نامعتبر است"),
    PENDING(62, "خطای بانکی "),
    INSUFFICIENT_FUNDS(106, "شناسه همگام سازی تکراری میباشد"),
    SYSTEM_ERROR(108, "انتقال یا تراکنش مورد نظر یافت نشد");

    private final int code;
    private final String message;

    PaymentResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getMessage(int code) {
        for (PaymentResultCode resultCode : PaymentResultCode.values()) {
            if (resultCode.getCode() == code) {
                return resultCode.message;
            }
        }
        throw new IllegalArgumentException("Unexpected code: " + code);
    }
}
