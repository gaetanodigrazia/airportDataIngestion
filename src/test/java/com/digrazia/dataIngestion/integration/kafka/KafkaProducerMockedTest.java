package com.digrazia.dataIngestion.integration.kafka;

import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerMockedTest {
    @Mock
    private KafkaTemplate<String, List<FlightInfoEntity>> flightKafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Mock
    private KafkaTemplate<String, AirportEntity> airportKafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendFlightInfo() {
        FlightInfoEntity flightInfoEntity = new FlightInfoEntity();
        flightInfoEntity.setIcao24("icao24");
        List<FlightInfoEntity> flightInfoEntityList = List.of(flightInfoEntity);

        kafkaProducer.sendFlightInfo(flightInfoEntityList);

        verify(flightKafkaTemplate, times(1)).send("flights", flightInfoEntityList);
    }

    @Test
    void testSendAirportInfo() {
        AirportEntity airportEntity = new AirportEntity();
        airportEntity.setIcao("EDDF");

        kafkaProducer.sendAirportInfo(airportEntity);

        verify(airportKafkaTemplate, times(1)).send("airports", airportEntity.getIcao(), airportEntity);
    }


}