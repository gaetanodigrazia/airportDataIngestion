package com.digrazia.dataIngestion.integration.kafka;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerMockedTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    @Disabled
    public void testSendMessage() {
        String FLIGHTS_KAFKA_TOPIC = "flights";
        String message = "test-message";

        kafkaProducer.sendFlightInfo(message);

        verify(kafkaTemplate, times(1)).send(FLIGHTS_KAFKA_TOPIC, message);
    }

    @Test
    @Disabled
    public void tesSendAirportInfo() {
        String AIRPORT_KAFKA_TOPIC = "airports";
        String message = "test-message";

        kafkaProducer.sendAirportInfo(message);

        verify(kafkaTemplate, times(1)).send(AIRPORT_KAFKA_TOPIC, message);
    }
}