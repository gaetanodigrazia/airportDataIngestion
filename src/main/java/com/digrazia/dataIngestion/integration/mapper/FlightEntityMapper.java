package com.digrazia.dataIngestion.integration.mapper;

import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FlightEntityMapper {

    public FlightEntityMapper(){

    }

    public static FlightEntity fromStringToFlightEntity(String json) {
        ObjectMapper mapper = new ObjectMapper();
        FlightEntity flightEntity = null;

        if (json != null) {
            try {
                flightEntity = mapper.readValue(json, FlightEntity.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return flightEntity;
    }
}
