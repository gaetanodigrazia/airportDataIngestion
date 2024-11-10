package com.digrazia.dataIngestion.integration.mapper;

import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

public class AirportEntityMapper {

    public AirportEntityMapper(){

    }

    public static List<AirportEntity> fromStringToAirportEntity(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<AirportEntity> airportEntityList = null;

        if (json != null) {
            try {
                airportEntityList = mapper.readValue(json, new TypeReference<List<AirportEntity>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return airportEntityList;
    }
}