package com.digrazia.dataIngestion.business.service.impl;

import com.digrazia.dataIngestion.business.service.AirportService;
import com.digrazia.dataIngestion.integration.kafka.KafkaProducer;
import com.digrazia.dataIngestion.integration.webclient.AirportWebClient;
import com.digrazia.dataIngestion.integration.webclient.FlightWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class AirportServiceImpl implements AirportService {

    private final KafkaProducer kafkaProducer;
    private final AirportWebClient airportWebClient;
    private final FlightWebClient flightWebClient;

    @Autowired
    public AirportServiceImpl(KafkaProducer kafkaProducer, AirportWebClient airportWebClient, FlightWebClient flightWebClient) {
        this.kafkaProducer = kafkaProducer;
        this.airportWebClient = airportWebClient;
        this.flightWebClient = flightWebClient;
    }

    @Override
    public void sendFlightData(long startTime, long endTime) {
        String response = flightWebClient.getAllFlights(startTime, endTime).toString();
        kafkaProducer.sendFlightInfo(response);
    }

    @Override
    public void sendAirportInfoData(String airportIcao)  {
        String response = airportWebClient.getAirportInfo(airportIcao).toString();
        kafkaProducer.sendAirportInfo(response);
    }


    @Scheduled(fixedRate = 3600000)
    private void sendHourlyFlightData(){
        long startTime = getEpochFromLocalDateTime(LocalDateTime.now(), 2);
        long endTime = getEpochFromLocalDateTime(LocalDateTime.now(), 0);
        sendFlightData(startTime, endTime);
    }

    @Scheduled(fixedRate = 259200000)
    private void sendAllAirportInfoData(){
        List<String> airportIcaoList = getAirportICAOList();
        airportIcaoList
                .stream()
                .forEach(icao -> sendAirportInfoData(icao));
    }

    private long getEpochFromLocalDateTime(LocalDateTime localDateTime, int hourOffset) {
        if (hourOffset != 0) {
            localDateTime = localDateTime.minusHours(hourOffset);
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private static List<String> getAirportICAOList() {
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
