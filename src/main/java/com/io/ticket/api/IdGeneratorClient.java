package com.io.ticket.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "id-generator", url = "${randon.string.generator.url}")
public interface IdGeneratorClient {
    @GetMapping("/randomstring")
    String generateTicketId();
}
