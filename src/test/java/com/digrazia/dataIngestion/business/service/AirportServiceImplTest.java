package com.digrazia.dataIngestion.business.service;

import com.digrazia.dataIngestion.business.service.impl.AirportServiceImpl;
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


public class AirportServiceImplTest {
    private static final String KAFKA_TOPIC = "airports";

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
    void testSendAirportInfoData() {
        String airportIcao = "EDDF";

        String response = "[{\"icao\":\"EDDF\", \"iata\":\"FRA\", \"name\":\"Frankfurt am Main International Airport\"}]";

        when(airportWebClient.getAirportInfo(airportIcao)).thenReturn(response);

        airportService.sendAirportInfoData(airportIcao);

        AirportEntity expectedAirportEntity = AirportEntityMapper.fromStringToAirportEntity(response);
        verify(kafkaProducer, times(1)).sendAirportInfo(expectedAirportEntity);
    }

}