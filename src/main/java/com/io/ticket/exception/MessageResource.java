package com.io.ticket.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageResource {
    public static final String PAYMENT_LINK_STRING = "از لینک اقدام به پرداخت نمایید : %s";
    public static final String TICKET_PURCHASE_SUCCESSFUL= "بلیط به شماره فاکتور %s صادر شد.";
    public static final String TICKET_PURCHASE_UNSUCCESSFUL= "امکان صدور بلیط وجود ندارد.";
}
