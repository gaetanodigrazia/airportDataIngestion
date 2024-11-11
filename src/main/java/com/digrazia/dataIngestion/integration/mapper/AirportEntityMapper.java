package com.digrazia.dataIngestion.integration.mapper;

import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class AirportEntityMapper {

    public AirportEntityMapper(){

    }

    public static AirportEntity fromStringToAirportEntity(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<AirportEntity> airportEntityList = null;

        try {
            airportEntityList = mapper.readValue(json, new TypeReference<List<AirportEntity>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la deserializzazione del JSON", e);
        }

        return airportEntityList.get(0);
    }
}