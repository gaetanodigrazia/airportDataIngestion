package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.AirportService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AirportServiceImpl implements AirportService {
    private static final String KAFKA_TOPIC = "ACTUAL_POSITIONS";

    private final KafkaProducer kafkaProducer;

    @Autowired
    public AirportServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void sendData(String jsonData) {
        kafkaProducer.sendMessage(KAFKA_TOPIC, jsonData);
    }
}
