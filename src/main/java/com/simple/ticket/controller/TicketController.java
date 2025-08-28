package com.simple.ticket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.simple.simpleLib.dto.ExtendedEventDto;
import com.simple.simpleLib.dto.ReserveDto;
import com.simple.simpleLib.dto.SimpleEventDto;
import com.simple.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("ticket")
public class TicketController {

    @Autowired
    TicketService service;

    /**
     * Requests events from Partner microservice
     *
     * @return returns a list of events
     * @throws JsonProcessingException if partner response is erroneous
     */
    @GetMapping("/getEvents")
    @ResponseStatus(HttpStatus.OK)
    public ExtendedEventDto getEvents() throws JsonProcessingException {
        return service.getEvents();
    }

    /**
     * Requests event by id from Partner microservice
     *
     * @param eventId id of event to be requested
     * @return dto of event or exception if not found
     * @throws JsonProcessingException if partner response is erroneous
     */
    @GetMapping("/getEvent/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public SimpleEventDto getEventById(@PathVariable Long eventId) throws JsonProcessingException {
        return service.getEventById(eventId);
    }

    /**
     * Tries to reserve a seat on an event
     *
     * @param eventId id of event for reservation
     * @param seatId  id of seat to be reserved
     * @return success, and if so, reservationId. Otherwise exception
     * @throws JsonProcessingException if partner response is erroneous
     */
    @PostMapping("/reserve/{eventId}/{seatId}")
    @ResponseStatus(HttpStatus.OK)
    public ReserveDto reserve(@PathVariable Long eventId, @PathVariable String seatId) throws JsonProcessingException {
        return service.reserveByEventAndSeat(eventId, seatId);
    }
}
