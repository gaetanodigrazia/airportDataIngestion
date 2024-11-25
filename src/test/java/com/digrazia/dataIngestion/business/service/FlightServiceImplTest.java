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





    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}