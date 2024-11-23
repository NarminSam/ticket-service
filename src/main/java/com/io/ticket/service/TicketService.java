package com.io.ticket.service;

import com.io.ticket.api.HolidayCheckerClient;
import com.io.ticket.api.IdGeneratorClient;
import com.io.ticket.entity.TicketSale;
import com.io.ticket.exception.ExceptionResource;
import com.io.ticket.exception.MessageResource;
import com.io.ticket.util.StringReformUtil;
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
        try {
            validateDate(date);
            var factorId = idGeneratorClient.generateTicketId();
            factorId = StringReformUtil.reformString(factorId);
            var paymentUrl = processPayment(factorId);
            ticketSaleService.saveTicketSale(TicketSale.init(date, factorId));
            return String.format(MessageResource.PAYMENT_LINK_STRING, paymentUrl);
        }catch(Exception e) {
            throw new IllegalArgumentException(ExceptionResource.GENERAL_ERROR);
        }
    }

    private String processPayment(String factorId) {
        return paymentService.initiatePayment(factorId).getPaymentLink();
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
        return holidayCheckerClient.checkHoliday(StringReformUtil.reformatDate(date));
    }

}
