package com.digrazia.dataIngestion.business.service;

import com.digrazia.dataIngestion.business.service.impl.AirportServiceImpl;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AirportServiceImplTest {
    private static final String KAFKA_TOPIC = "Flights";

    @Mock
    private AirportWebClient airportWebClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private AirportServiceImpl airportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendFlightData() {
        String mockResponse = "flight-data-response";
        Map<String, Object> mapResponse = Map.of("response", mockResponse);
        List<Map<String, Object>> expectedResponse = List.of(mapResponse);
        long startTime = airportService.getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = airportService.getEpochFromLocalDateTime(LocalDateTime.now(), 0);


        when(airportWebClient.getAllFlights(startTime, endTime))
                .thenReturn(List.of(mapResponse));

        airportService.sendFlightData();

        verify(airportWebClient, times(1)).getAllFlights(startTime, endTime);
        verify(kafkaProducer, times(1)).sendMessage(KAFKA_TOPIC, expectedResponse.toString());
    }
}