package com.digrazia.dataIngestion.integration.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Component
public class FlightWebClient {

    private final RestTemplate restTemplate;

    @Autowired
    public FlightWebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> getAllFlights(long begin, long end) {
        String url = "https://opensky-network.org/api/flights/all";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("begin", begin)
                .queryParam("end", end);

        System.out.println("API Call: " + uriBuilder.toUriString());
        List<Map<String, Object>> flights =
                restTemplate.getForObject(uriBuilder.toUriString(), List.class);
        return flights;
    }

}