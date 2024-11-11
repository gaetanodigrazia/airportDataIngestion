package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.AirportService;
import com.digrazia.dataIngestion.business.service.FlightService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.mapper.AirportEntityMapper;
import com.digrazia.dataIngestion.integration.mapper.FlightInfoEntityMapper;
import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
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
    public void sendFlightInfoData(long startTime, long endTime) {
        String response = flightWebClient.getAllFlights(startTime, endTime);

        List<FlightInfoEntity> flightInfoEntityList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
        kafkaProducer.sendFlightInfo(flightInfoEntityList);
    }

    @Scheduled(fixedRate = 3600000)
    private void sendHourlyFlightData(){
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);
        sendFlightInfoData(startTime, endTime);
    }

    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
