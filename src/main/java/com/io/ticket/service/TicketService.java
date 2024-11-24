package com.io.ticket.service;

import com.io.ticket.api.HolidayCheckerClient;
import com.io.ticket.api.IdGeneratorClient;
import com.io.ticket.common.TicketStatusCode;
import com.io.ticket.entity.TicketSale;
import com.io.ticket.exception.ExceptionResource;
import com.io.ticket.exception.MessageResource;
import com.io.ticket.repo.TicketSaleRepository;
import com.io.ticket.util.StringReformUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final HolidayCheckerClient holidayCheckerClient;
    private final IdGeneratorClient idGeneratorClient;
    private final PaymentService paymentService;
    private final TicketSaleService ticketSaleService;
    private final TicketSaleRepository ticketSaleRepository;

    @Value("${ezpay.expiration.time}")
    private int EZPAY_EXPIRATION_TIME;

    /**
     * Handles the ticket-selling process for a given date.
     * Validates the date, generates a unique factor ID, processes payment,
     * saves an initial ticket sale record, and returns the payment link.
     *
     * @param date the date for which the ticket is being sold (expected format: yyyyMMdd)
     * @return the payment URL for the user to complete the transaction
     * @throws IllegalArgumentException if the process fails due to validation or any other error
     */
    public String sellTicket(String date) {
        try {
            validateDate(date);
            var factorId = StringReformUtil.reformString(idGeneratorClient.generateTicketId());
            var paymentUrl = processPayment(factorId);
            ticketSaleRepository.save(TicketSale.init(date, factorId, TicketStatusCode.IN_PROGRESS));
            return String.format(MessageResource.PAYMENT_LINK_STRING, EZPAY_EXPIRATION_TIME/60, paymentUrl);
        }catch(Exception e) {
            throw new IllegalArgumentException(ExceptionResource.GENERAL_ERROR);
        }
    }

    /**
     * Validates the provided date for ticket sales.
     * Ensures the date is in the correct format, has available tickets, and is a valid holiday.
     *
     * @param date the date to validate (expected format: yyyyMMdd)
     * @throws IllegalArgumentException if the date is invalid, tickets are sold out,
     *                                  or the date is not a valid holiday
     */
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

    /**
     * Checks if the given date is a holiday by querying the external holiday service.
     * Results are cached for improved performance.
     *
     * @param date the date to check (expected format: yyyyMMdd)
     * @return a map containing holiday status and related information
     */
    @Cacheable(value = "holidays")
    public Map<String, Object> checkHoliday(String date){
        return holidayCheckerClient.checkHoliday(StringReformUtil.reformatDate(date));
    }

    /**
     * Initiates the payment process for the given factor ID and retrieves the payment link.
     *
     * @param factorId the unique identifier for the ticket factor
     * @return the payment link to complete the transaction
     * @throws IllegalStateException if the payment initiation fails
     */
    private String processPayment(String factorId) {
        return paymentService.initiatePayment(factorId).getPaymentLink();
    }

}
