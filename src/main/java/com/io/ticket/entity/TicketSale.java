package com.io.ticket.entity;

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

    public static TicketSale init(String date ,String factor) {
        return TicketSale.builder()
                .date(date)
                .factor(factor)
                .build();
    }

    public void update( String requestUid) {
        this.requestUid = requestUid != null ? requestUid : this.requestUid;
    }



}
