package com.io.ticket.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "random-string-api", url = "http://www.randomnumberapi.com/api/v1.0")
public interface IdGeneratorClient {
    @GetMapping("/randomstring")
    String generateTicketId();
}
