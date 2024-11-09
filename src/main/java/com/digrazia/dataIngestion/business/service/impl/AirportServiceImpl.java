package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.AirportService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;



@Service
public class AirportServiceImpl implements AirportService {
    private static final String KAFKA_TOPIC = "Flights";

    private final KafkaProducer kafkaProducer;
    private final AirportWebClient airportWebClient;

    @Autowired
    public AirportServiceImpl(KafkaProducer kafkaProducer, AirportWebClient airportWebClient) {
        this.kafkaProducer = kafkaProducer;
        this.airportWebClient = airportWebClient;
    }

    @Override
    @Scheduled(fixedRate = 3600000)
    public void sendFlightData() {
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);

        String response = airportWebClient.getAllFlights(startTime, endTime).toString();

        kafkaProducer.sendMessage(KAFKA_TOPIC, response);
    }

    public long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

}
