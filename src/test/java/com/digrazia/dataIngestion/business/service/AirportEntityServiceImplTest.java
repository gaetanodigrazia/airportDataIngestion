package com.digrazia.dataIngestion.business.service;

import com.digrazia.dataIngestion.business.service.impl.AirportServiceImpl;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import com.digrazia.dataIngestion.integration.webclient.FlightWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class AirportEntityServiceImplTest {
    private static final String KAFKA_TOPIC = "Flights";

    @Mock
    private FlightWebClient flightWebClient;

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
    public void sendFlightData_whenOk() {
        String mockResponse = "flight-data-response";
        Map<String, Object> mapResponse = Map.of("response", mockResponse);
        List<Map<String, Object>> expectedResponse = List.of(mapResponse);
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);

        when(flightWebClient.getAllFlights(startTime, endTime))
                .thenReturn(List.of(mapResponse));

        airportService.sendFlightData(startTime, endTime);

        verify(flightWebClient, times(1)).getAllFlights(startTime, endTime);
        verify(kafkaProducer, times(1)).sendFlightInfo(expectedResponse.toString());
    }

    @Test
    public void sendAirportInfoData_whenOk() {
        String airportIcao = "LIRF";

        String mockResponse = "mocked airport info";
        when(airportWebClient.getAirportInfo(anyString())).thenReturn(mockResponse);

        airportService.sendAirportInfoData(airportIcao);

        verify(airportWebClient).getAirportInfo(airportIcao);

        verify(kafkaProducer, times(1)).sendAirportInfo(mockResponse);
    }

    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}