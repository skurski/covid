package com.skurski.opendata.covid.domain.controller;

import com.skurski.opendata.covid.domain.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/covid")
@Slf4j
@RequiredArgsConstructor
public class DownloadController {

    private static final String COVID_CSV_FILE_SUCCESSFULLY_DOWNLOADED = "Covid CSV file successfully downloaded.";
    private static final String COVID_FULL_DATA_PROCESS_SUCCESSFULLY = "Covid full data process successfully.";
    private static final String COVID_DELTA_LOAD_PROCESS_SUCCESSFULLY = "Covid delta load process successfully.";

    private final OpenDataRestClient openDataRestClient;

    private final StatisticService statisticService;

    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
        log.info("Hello from Covid App - application is up and running");
        return createSuccessfulResponse("Hello from Covid App - application is up and running");
    }

    @GetMapping(value = "/download")
    public ResponseEntity<String> getStatistics() {
        log.info("Fetching all covid statistics from open data REST API");
        openDataRestClient.downloadCovidStatistics();
        log.info("Fetched all statistics.");

        return createSuccessfulResponse(COVID_CSV_FILE_SUCCESSFULLY_DOWNLOADED);
    }

    @GetMapping(value = "/process/full")
    public ResponseEntity<String> processFullDownloadedData() {
        log.info("Starting full load process to persist all covid data");
        statisticService.loadStatistics();
        log.info("Full load ended successfully - all data persisted in database");

        return createSuccessfulResponse(COVID_FULL_DATA_PROCESS_SUCCESSFULLY);
    }

    @GetMapping(value = "/process/delta")
    public ResponseEntity<String> processDeltaLoad() {

        return createSuccessfulResponse(COVID_DELTA_LOAD_PROCESS_SUCCESSFULLY);
    }

    private <T> ResponseEntity<T> createSuccessfulResponse(T body) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
