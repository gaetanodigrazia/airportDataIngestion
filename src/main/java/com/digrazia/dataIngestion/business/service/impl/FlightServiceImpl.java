package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.FlightService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.mapper.FlightInfoEntityMapper;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import com.digrazia.dataIngestion.integration.webclient.FlightWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
        List<String> airportIcaoList = getAirportICAOList();
        airportIcaoList
                .stream()
                .forEach(icao -> {
                    String response = flightWebClient.getAllDeparture(startTime, endTime, icao);
                    List<FlightInfoEntity> flightInfoEntityList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
                    kafkaProducer.sendDepartureInfo(flightInfoEntityList);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread interrotto " + icao);
                    }
                });
    }

    @Override
    public void sendArrival(long startTime, long endTime) {
        List<String> airportIcaoList = getAirportICAOList();
        airportIcaoList
                .stream()
                .forEach(icao -> {
                    String response = flightWebClient.getAllArrival(startTime, endTime, icao);
                    List<FlightInfoEntity> flightInfoEntityList = FlightInfoEntityMapper.fromStringToFlightInfoEntitList(response);
                    kafkaProducer.sendArrivalInfo(flightInfoEntityList);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Thread interrotto " + icao);
                    }
                });
    }

    @Scheduled(fixedRate = 86472000)
    private void sendDailyDepartureData(){
        long startTime = getEpochFromLocalDateTime(true);
        long endTime = getEpochFromLocalDateTime(false);
        sendDeparture(startTime, endTime);
    }

    @Scheduled(fixedRate = 86472000)
    private void sendDailyArrivalData(){
        long startTime = getEpochFromLocalDateTime(true);
        long endTime = getEpochFromLocalDateTime(false);
        sendArrival(startTime, endTime);
    }

    private long getEpochFromLocalDateTime(boolean isBeginning) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (isBeginning) {
            localDateTime = localDateTime.minusDays(1L);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private List<String> getAirportICAOList() {
        return List.of(
                // Italia
                "LIRF", // Roma Fiumicino
                "LIMC", // Milano Malpensa
                "LIML", // Milano Linate
                "LIPZ", // Venezia Marco Polo
                "LIRN", // Napoli Capodichino
                "LIMF", // Torino Caselle
                "LIPE", // Bologna Guglielmo Marconi
                "LIRQ", // Firenze Peretola
                "LICC", // Catania Fontanarossa
                "LICJ", // Palermo Punta Raisi

                // Regno Unito
                "EGLL", // Londra Heathrow
                "EGKK", // Londra Gatwick
                "EGSS", // Londra Stansted
                "EGGW", // Londra Luton
                "EGLC", // Londra City
                "EGCC", // Manchester
                "EGBB", // Birmingham
                "EGPH", // Edimburgo
                "EGPF", // Glasgow
                "EGGD", // Bristol

                // Francia
                "LFPG", // Parigi Charles de Gaulle
                "LFPO", // Parigi Orly
                "LFMN", // Nizza Costa Azzurra

                // Svizzera
                "LSZH", // Zurigo
                "LSGG", // Ginevra
                "LFSB", // Basilea-Mulhouse-Friburgo

                // Austria
                "LOWW", // Vienna
                "LOWI", // Innsbruck

                // Slovenia
                "LJLJ", // Lubiana Jože Pučnik

                // Germania
                "EDDF", // Francoforte
                "EDDM", // Monaco di Baviera
                "EDDS", // Stoccarda

                // Croazia
                "LDZA", // Zagabria
                "LDRI", // Fiume

                // Dubai
                "OMDB", // Dubai International Airport
                "OMDW", // Al Maktoum International Airport

                // New York
                "KJFK", // John F. Kennedy International Airport
                "KLGA", // LaGuardia Airport
                "KEWR"  // Newark Liberty International Airport
        );
    }

}
