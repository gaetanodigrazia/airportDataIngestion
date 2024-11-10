package com.digrazia.dataIngestion.integration.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String FLIGHTS_KAFKA_TOPIC = "flights";
    private static final String AIRPORT_KAFKA_TOPIC = "airports";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        System.out.println("Sending message to topic " + topic + " with message " + message);
        kafkaTemplate.send(topic, message);
    }

    public void sendFlightInfo(String message) {
        System.out.println("Sending message to topic " + FLIGHTS_KAFKA_TOPIC + " with message " + message);
        kafkaTemplate.send(FLIGHTS_KAFKA_TOPIC, message);
    }

    public void sendAirportInfo(String message) {
        System.out.println("Sending message to topic " + AIRPORT_KAFKA_TOPIC + " with message " + message);
        kafkaTemplate.send(AIRPORT_KAFKA_TOPIC, message);
    }


}
