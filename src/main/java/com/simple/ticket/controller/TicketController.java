package com.simple.ticket.controller;

import com.simple.simpleLib.dto.ExtendedEventDto;
import com.simple.simpleLib.dto.SimpleEventDto;
import com.simple.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("ticket")
public class TicketController {

    @Autowired
    TicketService service;

    /* TODO
        Legyen lehetőség egy adott esemény adott helyére szóló jegyet megvásárolni egy tárolt kártyával (cardId)
        Ha egy adott szék már foglalt, ne engedjen jegyet vásárolni rá
        Ha egy adott esemény már elkezdődött, ne engedjen jegyet vásárolni rá
        Ha a felhasználónak nincs elég fedezete, ne engedjen jegyet vásárolni
        Alakítsd ki a hibakezelési folyamatokat
     */

    @GetMapping("/getEvents")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ExtendedEventDto> getEvents() {
        return service.getEvents();
    }

    @GetMapping("/getEvent/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SimpleEventDto> getEventById(@PathVariable Long eventId) {
        return service.getEventById(eventId);
    }
}
