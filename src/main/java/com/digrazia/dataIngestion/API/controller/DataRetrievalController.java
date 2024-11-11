package com.digrazia.dataIngestion.API.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingestion")
public class DataRetrievalController {

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        return ResponseEntity.badRequest().build();
    }


}
