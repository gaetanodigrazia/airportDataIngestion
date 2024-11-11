package com.digrazia.dataIngestion.business.service;

public interface AirportService {

    void sendFlightInfoData(long startTime, long endTime);

    void sendAirportInfoData(String airportIcao);

}
