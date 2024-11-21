package com.digrazia.dataIngestion.integration.webclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FlightWebClient {
    Logger logger = LoggerFactory.getLogger(FlightWebClient.class);
    private RestTemplate restTemplate;

    @Autowired
    public FlightWebClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAllDeparture(long begin, long end, String airportIcao) {
        String url = "https://opensky-network.org/api/flights/departure";
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                    .queryParam("begin", begin)
                    .queryParam("end", end)
                    .queryParam("airport", airportIcao);
            System.out.println(uriBuilder.toUriString());
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Void> entity = new HttpEntity<>(headers);

             response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

        } catch (HttpClientErrorException notFound) {
            logger.error("No departure found for airport: " + airportIcao);
        }

        return response.getBody();
    }

    public String getAllArrival(long begin, long end, String airportIcao) {
        String url = "https://opensky-network.org/api/flights/arrival";
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        try {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("begin", begin)
                .queryParam("end", end)
                .queryParam("airport", airportIcao);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );
        } catch (HttpClientErrorException notFound) {
            logger.error("No arrival found for airport: " + airportIcao);
        }

        return response.getBody();
    }

}