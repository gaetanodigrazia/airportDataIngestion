package com.digrazia.dataIngestion.business.service;

public interface AirportService {

    void sendFlightData(long startTime, long endTime);

    void sendAirportInfoData(String airportIcao);

}
