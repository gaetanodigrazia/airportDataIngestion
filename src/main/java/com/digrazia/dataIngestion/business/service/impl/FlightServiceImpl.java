package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.FlightService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.mapper.FlightInfoEntityMapper;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import com.digrazia.dataIngestion.integration.webclient.FlightWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final KafkaProducer kafkaProducer;
    private final FlightWebClient flightWebClient;

    @Autowired
    public FlightServiceImpl(KafkaProducer kafkaProducer, FlightWebClient flightWebClient) {
        this.kafkaProducer = kafkaProducer;
        this.flightWebClient = flightWebClient;
    }

    @Override
    public void sendDeparture(long startTime, long endTime) {
        String response = flightWebClient.getAllDeparture(startTime, endTime);

        List<FlightInfoEntity> flightInfoEntityList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
        kafkaProducer.sendFlightInfo(flightInfoEntityList);
    }

    @Override
    public void sendArrival(long startTime, long endTime) {
        String response = flightWebClient.getAllArrival(startTime, endTime);

        List<FlightInfoEntity> flightInfoEntityList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
        kafkaProducer.sendFlightInfo(flightInfoEntityList);
    }

    @Scheduled(fixedRate = 86400000)
    private void sendDailyDepartureData(){
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 24);
        sendDeparture(startTime, endTime);
    }

    @Scheduled(fixedRate = 86400000)
    private void sendDailyArrivalData(){
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 24);
        sendArrival(startTime, endTime);
    }

    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
