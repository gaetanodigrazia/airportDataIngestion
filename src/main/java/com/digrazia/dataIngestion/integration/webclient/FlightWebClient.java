package com.digrazia.dataIngestion.integration.webclient;

import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    public String getAllFlights(long begin, long end) {
        String url = "https://opensky-network.org/api/flights/all";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("begin", begin)
                .queryParam("end", end);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }

}