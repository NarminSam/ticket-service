package com.io.ticket.entity;

import com.io.ticket.common.TicketStatusCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_sales")
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TicketSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "factor", unique = true)
    private String factor;

    @Column(name = "request-uid", unique = true)
    private String requestUid;

    @Column(name = "transaction-number", unique = true)
    private String transactionNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatusCode status;

    public static TicketSale init(String date ,String factor, TicketStatusCode status) {
        return TicketSale.builder()
                .date(date)
                .factor(factor)
                .status(status)
                .build();
    }

    public void update( String requestUid, String transactionNumber, TicketStatusCode status) {
        this.requestUid = requestUid != null ? requestUid : this.requestUid;
        this.transactionNumber = transactionNumber != null ? transactionNumber : this.transactionNumber;
        this.status = status;
    }



}
