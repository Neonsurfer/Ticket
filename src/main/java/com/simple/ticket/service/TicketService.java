package com.simple.ticket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simple.simpleLib.dto.ExtendedEventDto;
import com.simple.simpleLib.dto.ReserveDto;
import com.simple.simpleLib.dto.SimpleEventDto;
import com.simple.ticket.error.BusinessException;
import com.simple.ticket.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
public class TicketService {
    private static String ENCODED_AUTH;
    private final WebClient partnerWebClient;
    @Autowired
    ObjectMapper objectMapper;

    public TicketService(WebClient.Builder builder,
                         @Value("${partner.module.host}") String partnerHost,
                         @Value("${partner.module.port}") String partnerPort,
                         @Value("${partner.module.username}") String partnerUsername,
                         @Value("${partner.module.password}") String partnerPassword) {
        this.partnerWebClient = builder.baseUrl("http://" + partnerHost + ":" + partnerPort).build();
        ENCODED_AUTH = Base64.getEncoder().encodeToString((partnerUsername + ":" + partnerPassword).getBytes(StandardCharsets.UTF_8));
    }

    public Mono<ExtendedEventDto> getEvents() throws JsonProcessingException {
        try {
            return partnerWebClient.get()
                    .uri("/partner/getEvents")
                    .header("Authorization", "Basic " + ENCODED_AUTH)
                    .retrieve()
                    .bodyToMono(ExtendedEventDto.class);
        } catch (WebClientResponseException e) {
            ErrorResponse response = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            throw new BusinessException(response.getErrorMessage(), response.getErrorCode());
        }
    }


    public Mono<SimpleEventDto> getEventById(Long eventId) throws JsonProcessingException {
        try {
            return partnerWebClient.get()
                    .uri("/partner/getEvent/{id}", eventId)
                    .header("Authorization", "Basic " + ENCODED_AUTH)
                    .retrieve()
                    .bodyToMono(SimpleEventDto.class);
        } catch (WebClientResponseException e) {
            ErrorResponse response = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            throw new BusinessException(response.getErrorMessage(), response.getErrorCode());
        }
    }

    public ReserveDto reserveByEventAndSeat(Long eventId, String seatId) throws JsonProcessingException {
        try {
            return partnerWebClient.post()
                    .uri("/partner/reserve/{eventId}/{seatId}", eventId, seatId)
                    .header("Authorization", "Basic " + ENCODED_AUTH)
                    .retrieve()
                    .bodyToMono(ReserveDto.class)
                    .block();
        } catch (WebClientResponseException e) {
            ErrorResponse response = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
            throw new BusinessException(response.getErrorMessage(), response.getErrorCode());
        }
    }
}
