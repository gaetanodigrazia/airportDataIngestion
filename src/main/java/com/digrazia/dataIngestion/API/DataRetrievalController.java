package com.digrazia.dataIngestion.API;

import com.digrazia.dataIngestion.business.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/ingestion")
public class DataRetrievalController {

    @GetMapping("/data")
    public ResponseEntity<String> getData() {
        return ResponseEntity.badRequest().build();
    }


}
