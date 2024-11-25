package com.digrazia.dataIngestion.integration.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
    private static final String DEPARTURES_KAFKA_TOPIC = "departures";
    private static final String ARRIVALS_KAFKA_TOPIC = "arrivals";
    private static final String AIRPORT_KAFKA_TOPIC = "airports";

    @Autowired
    KafkaTemplate<String, String> airportKafkaTemplate;

    @Autowired
    KafkaTemplate<String, String> flightKafkaTemplate;

    public KafkaProducer() {}

    public void sendArrivalInfo(String flightInfoEntityList) {
        System.out.println("Sending message to topic " + ARRIVALS_KAFKA_TOPIC + " with message " + flightInfoEntityList);
        flightKafkaTemplate.send(ARRIVALS_KAFKA_TOPIC, flightInfoEntityList);
    }

    public void sendDepartureInfo(String flightInfoEntityList) {
        System.out.println("Sending message to topic " + DEPARTURES_KAFKA_TOPIC + " with message " + flightInfoEntityList);
        flightKafkaTemplate.send(DEPARTURES_KAFKA_TOPIC, flightInfoEntityList);
    }

    public void sendAirportInfo(String airportEntity) {
        System.out.println("Sending message to topic " + AIRPORT_KAFKA_TOPIC + " with message " + airportEntity);
        airportKafkaTemplate.send(AIRPORT_KAFKA_TOPIC, airportEntity);
    }


}
