package com.simple.ticket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simple.simpleLib.dto.ExtendedEventDto;
import com.simple.simpleLib.dto.ReserveDto;
import com.simple.simpleLib.dto.SimpleEventDto;
import com.simple.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController("ticket")
public class TicketController {

    @Autowired
    TicketService service;

    @GetMapping("/getEvents")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ExtendedEventDto> getEvents() throws JsonProcessingException {
        return service.getEvents();
    }

    @GetMapping("/getEvent/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SimpleEventDto> getEventById(@PathVariable Long eventId) throws JsonProcessingException {
        return service.getEventById(eventId);
    }

    @PostMapping("/reserve/{eventId}/{seatId}")
    @ResponseStatus(HttpStatus.OK)
    public ReserveDto reserve(@PathVariable Long eventId, @PathVariable String seatId) throws JsonProcessingException {
        return service.reserveByEventAndSeat(eventId, seatId);
    }
}
