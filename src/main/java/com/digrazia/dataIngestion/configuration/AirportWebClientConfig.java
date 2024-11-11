package com.digrazia.dataIngestion.configuration;

import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AirportWebClientConfig {

        @Autowired
        RestTemplate restTemplate;

        @Bean
        public AirportWebClient airportWebClient() {
            return new AirportWebClient(restTemplate);
        }

}
