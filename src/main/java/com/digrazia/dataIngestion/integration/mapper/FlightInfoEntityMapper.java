package com.digrazia.dataIngestion.integration.mapper;

import com.digrazia.dataIngestion.integration.model.AirportEntity;
import com.digrazia.dataIngestion.integration.model.FlightInfoEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlightInfoEntityMapper {

    public FlightInfoEntityMapper(){

    }

    public static List<FlightInfoEntity> fromStringToFlightInfoEntitList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<FlightInfoEntity> flightInfoEntityList = null;

        if (json != null) {
            try {
                flightInfoEntityList = mapper.readValue(json, new TypeReference<List<FlightInfoEntity>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return flightInfoEntityList;
    }
}