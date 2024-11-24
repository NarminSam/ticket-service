package com.io.ticket.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TicketStatusCode {
    IN_PROGRESS("inProgress"),
    FINALIZED("finalized");

    private final String title;
}

