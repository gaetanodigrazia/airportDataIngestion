package com.digrazia.dataIngestion.business.service;

public interface FlightService {

    void sendDeparture(long startTime, long endTime);
    void sendArrival(long startTime, long endTime);

}
