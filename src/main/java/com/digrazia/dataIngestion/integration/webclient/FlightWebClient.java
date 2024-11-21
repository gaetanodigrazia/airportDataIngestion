package com.digrazia.dataIngestion.integration.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FlightWebClient {

    private RestTemplate restTemplate;

    @Autowired
    public FlightWebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAllDeparture(long begin, long end, String airportIcao) {String url = "https://opensky-network.org/api/flights/departure";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("begin", begin)
                .queryParam("end", end)
                .queryParam("airport", airportIcao);
        System.out.println(uriBuilder.toUriString());
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

    public String getAllArrival(long begin, long end, String airportIcao) {
        String url = "https://opensky-network.org/api/flights/arrival";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("begin", begin)
                .queryParam("end", end)
                .queryParam("airport", airportIcao);

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