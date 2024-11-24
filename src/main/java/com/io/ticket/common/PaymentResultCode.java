package com.io.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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

}
