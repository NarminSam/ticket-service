package com.io.ticket.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "holiday-checker", url = "https://holidayapi.ir")
public interface HolidayCheckerClient {

    @GetMapping("/jalali/{date}")
    Map<String, Object> checkHoliday(@PathVariable("date") String date);

}
