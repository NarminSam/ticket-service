package com.io.ticket.service;

import com.io.ticket.common.TicketStatusCode;
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

    public boolean isDateAvailable(String date){
        return ticketSaleRepository.countByDate(date) < SALE_LIMIT_PER_DAY;
    }

    public boolean isPaymentDuplicate(String requestUid){
        return ticketSaleRepository.existsByRequestUidAndStatus(requestUid, TicketStatusCode.FINALIZED);
    }
}
