package com.io.ticket.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class ExceptionResource {
    public static final String INVALID_DATE_FORMAT = "قالب تاریخ نامعتبر است. فرمت مورد نظر به صورت yyyyMMdd می باشد.";
    public static final String TICKETS_SOLD_OUT = "بلیط برای تاریخ انتخاب شده موجود نمی باشد.";
    public static final String INVALID_HOLIDAY_DATE = "تنها برای روزهای تعطیل رسمی - جمعه و تعطیلات رسمی تقویمی- بلیت به فروش می رسد.";
    public static final String INVALID_PAYMENT_DETAILS = "امکان ثبت پرداخت به دلیل وچود پرداخت با شناسه یکتا امکان پذیر نمی باشد.";
    public static final String INVALID_PAYMENT_REQUEST = "امکان درخواست پرداخت وجود ندارد.";
    public static final String GENERAL_ERROR = "امکان انجام درخواست وجود ندارد.";
}
