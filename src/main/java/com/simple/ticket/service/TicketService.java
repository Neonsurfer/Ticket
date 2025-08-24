package com.simple.ticket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.simpleLib.dto.ExtendedEventDto;
import com.simple.simpleLib.dto.SimpleEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class TicketService {
    private static String ENCODED_AUTH;
    private final WebClient webClient;
    @Autowired
    ObjectMapper objectMapper;

    public TicketService(WebClient.Builder builder,
                         @Value("${partner.module.host}") String host,
                         @Value("${partner.module.port}") String port,
                         @Value("${partner.module.username}") String partnerUsername,
                         @Value("${partner.module.password}") String partnerPassword) {
        this.webClient = builder.baseUrl("http://" + host + ":" + port).build();
        ENCODED_AUTH = Base64.getEncoder().encodeToString((partnerUsername + ":" + partnerPassword).getBytes(StandardCharsets.UTF_8));
    }

    public Mono<ExtendedEventDto> getEvents() {
        return webClient.get()
                .uri("/partner/getEvents")
                .header("Authorization", "Basic " + ENCODED_AUTH)
                .retrieve()
                .bodyToMono(ExtendedEventDto.class);
    }


    public Mono<SimpleEventDto> getEventById(Long eventId) {
        return webClient.get()
                .uri("/partner/getEvent/{id}", eventId)
                .header("Authorization", "Basic " + ENCODED_AUTH)
                .retrieve()
                .bodyToMono(SimpleEventDto.class);
    }
}
