package com.io.ticket.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ticket_sales")
@Getter
@Setter
public class TicketSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "factor", unique = true)
    private int factor;

}
