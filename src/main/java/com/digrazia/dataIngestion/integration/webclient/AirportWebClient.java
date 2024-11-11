package com.digrazia.dataIngestion.integration.webclient;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class AirportWebClient {

    private final RestTemplate restTemplate;

    @Value("${custom.apiKey}")
    private String apiKey;

    private static final String AIRPORT_API_URL = "https://api.api-ninjas.com/v1/airports";

    @Autowired
    public AirportWebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAirportInfo(String airportIcao) {

        String aiportApiUrl = getAirportApiUrl(airportIcao);
        HttpEntity<String> entity = getHttpEntityWithHeaders();

        ResponseEntity<String> exchange = restTemplate.exchange(aiportApiUrl, HttpMethod.GET, entity, String.class);

        return exchange.getBody();
    }

    private String getAirportApiUrl(String airportIcao) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(AIRPORT_API_URL)
                .queryParam("icao", airportIcao);
        return uriBuilder.toUriString();
    }

    private HttpEntity<String> getHttpEntityWithHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return entity;
    }

}