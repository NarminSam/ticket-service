package com.io.ticket.repo;

import com.io.ticket.entity.TicketSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSaleRepository extends JpaRepository<TicketSale, Long> {

    Long countByDate(String date);
}
