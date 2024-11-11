package com.digrazia.dataIngestion.business.service;

import com.digrazia.dataIngestion.business.service.impl.AirportServiceImpl;
import com.digrazia.dataIngestion.business.service.impl.FlightServiceImpl;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.mapper.AirportEntityMapper;
import com.digrazia.dataIngestion.integration.mapper.FlightInfoEntityMapper;
import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import com.digrazia.dataIngestion.integration.webclient.FlightWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class FlightServiceImplTest {
    private static final String KAFKA_TOPIC = "flights";

    @Mock
    private FlightWebClient flightWebClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendFlightInfoData() {
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);

        String response = "[{\"icao24\":\"0ac424\", \"firstSeen\":1731343759, \"estDepartureAirport\":\"SKBO\"}]";

        when(flightWebClient.getAllFlights(startTime, endTime)).thenReturn(response);

        flightService.sendFlightInfoData(startTime, endTime);

        List<FlightInfoEntity> expectedFlightList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
        verify(kafkaProducer, times(1)).sendFlightInfo(expectedFlightList);
    }



    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}