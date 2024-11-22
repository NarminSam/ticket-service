package com.io.ticket.service;

import com.io.ticket.api.HolidayCheckerClient;
import com.io.ticket.api.IdGeneratorClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class TicketService {

    private final HolidayCheckerClient holidayCheckerClient;
    private final IdGeneratorClient idGeneratorClient;
    private final PaymentService paymentService;
    private final Map<String, Integer> ticketsSold = new ConcurrentHashMap<>();

    public String sellTicket(String date) {
        validateDate(date);
        // Check ticket availability
        ticketsSold.putIfAbsent(date, 0);
        if (ticketsSold.get(date) >= 10) {
            return "All tickets for this date are sold out.";
        }
        String ticketId = idGeneratorClient.generateTicketId();
        var paymentUrl = processPayment(ticketId);
        ticketsSold.put(date, ticketsSold.get(date) + 1);
        return "از لینک اقدام به پرداخت نمایید : " + paymentUrl;
    }

    private String processPayment(String ticketId) {
        return paymentService.initiatePayment(ticketId).getPaymentLink();
    }

    private void validateDate(String date){
        if (date == null || date.length() != 8) {
            throw new IllegalArgumentException("قالب تاریخ نامعتبر است. فرمت مورد نظر به صورت yyyyMMdd می باشد.");
        }

        Map<String, Object> holidayResponse = holidayCheckerClient.checkHoliday(reformatDate(date));
        if((Boolean) holidayResponse.getOrDefault("status", true)) {
            Boolean isHoliday = (Boolean) holidayResponse.getOrDefault("is_holiday", false);
            if (!isHoliday) {
                throw new IllegalArgumentException("تنها برای روزهای تعطیل رسمی - جمعه و تعطیلات رسمی تقویمی- بلیت به فروش می رسد.");
            }
        }
        else throw new IllegalArgumentException("قالب تاریخ نامعتبر است. فرمت مورد نظر به صورت yyyyMMdd می باشد.");
    }

    private String reformatDate(String date) {
        StringBuilder newDate = new StringBuilder()
                .append(date, 0, 4).append("/")  // Append year and slash
                .append(date, 4, 6).append("/")  // Append month and slash
                .append(date, 6, 8);             // Append day

        return newDate.toString();
}

}
