package com.io.ticket.service;

import com.io.ticket.entity.TicketSale;
import com.io.ticket.repo.TicketSaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketSaleService {
    private final TicketSaleRepository ticketSaleRepository;

    @Value("${sale.limit.perday}")
    private int SALE_LIMIT_PER_DAY;

    public void saveTicketSale(TicketSale ticketSale){
        ticketSaleRepository.save(ticketSale);
    }

    public boolean isDateAvailable(String date){
        return ticketSaleRepository.countByDate(date) < SALE_LIMIT_PER_DAY;
    }
}
