package com.io.ticket.service;

import com.io.ticket.api.HolidayCheckerClient;
import com.io.ticket.api.IdGeneratorClient;
import com.io.ticket.exception.ExceptionResource;
import com.io.ticket.util.DateReformUtil;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@AllArgsConstructor
public class TicketService {

    private final HolidayCheckerClient holidayCheckerClient;
    private final IdGeneratorClient idGeneratorClient;
    private final PaymentService paymentService;
    private final TicketSaleService ticketSaleService;

    public String sellTicket(String date) {
        validateDate(date);
        var paymentUrl = processPayment(idGeneratorClient.generateTicketId());
        return "از لینک اقدام به پرداخت نمایید : " + paymentUrl;
    }

    private String processPayment(String ticketId) {
        return paymentService.initiatePayment(ticketId).getPaymentLink();
    }

    private void validateDate(String date){
        if (date == null || date.length() != 8) {
            throw new IllegalArgumentException(ExceptionResource.INVALID_DATE_FORMAT);
        }

        if (!ticketSaleService.isDateAvailable(date)) {
            throw new IllegalArgumentException(ExceptionResource.TICKETS_SOLD_OUT);
        }

        Map<String, Object> holidayResponse = checkHoliday(date);
        if((Boolean) holidayResponse.getOrDefault("status", true)) {
            Boolean isHoliday = (Boolean) holidayResponse.getOrDefault("is_holiday", false);
            if (!isHoliday) {
                throw new IllegalArgumentException(ExceptionResource.INVALID_HOLIDAY_DATE);
            }
        }
        else throw new IllegalArgumentException(ExceptionResource.INVALID_DATE_FORMAT);
    }

    @Cacheable(value = "holidays", key = "#date", condition = "#date != null")
    public Map<String, Object> checkHoliday(String date){
        return holidayCheckerClient.checkHoliday(DateReformUtil.reformatDate(date));
    }

}
