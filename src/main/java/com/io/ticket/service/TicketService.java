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

        private final HolidayCheckerClient holidayApiClient;
        private final IdGeneratorClient randomStringApiClient;
        private final Map<String, Integer> ticketsSold = new ConcurrentHashMap<>();

        public String sellTicket(String date) {
            // Validate if the date is an official holiday
            if (date == null || date.length() != 8) {
                throw new IllegalArgumentException("Invalid date format. Expected yyyyMMdd.");
            }

            // Extract the year, month, and day from the date string
            String year = date.substring(0, 4);   // First 4 characters for year
            String month = date.substring(4, 6);  // Next 2 characters for month
            String day = date.substring(6, 8);
            StringBuilder newDate = new StringBuilder();
            newDate.append(year).append("/").append(month).append("/").append(day);
            Map<String, Object> holidayResponse = holidayApiClient.checkHoliday(newDate.toString());
            Boolean isHoliday = (Boolean) holidayResponse.getOrDefault("is_holiday", false);
            if (!isHoliday) {
                return "Tickets are only sold for official holidays.";
            }

            // Check ticket availability
            ticketsSold.putIfAbsent(date, 0);
            if (ticketsSold.get(date) >= 10) {
                return "All tickets for this date are sold out.";
            }

            // Simulate payment processing
            processPayment(1000);

            // Generate ticket ID
            String ticketId = randomStringApiClient.generateTicketId();
            ticketsSold.put(date, ticketsSold.get(date) + 1);

            return "Your ticket ID: " + ticketId;
        }

        private void processPayment(int amount) {
            // Mock payment gateway logic
            if (amount != 1000) {
                throw new RuntimeException("Payment failed");
            }
        }

}
