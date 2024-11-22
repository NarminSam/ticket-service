package com.io.ticket.model.request;

public record EzpayCallbackRequest(
        String factorNumber,
        String requestUid,
        String processUid,
        int statusCode,
        String statusDesc,
        int amount
) {}

