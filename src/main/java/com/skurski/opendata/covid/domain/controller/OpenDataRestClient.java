package com.skurski.opendata.covid.domain.controller;

import com.skurski.opendata.covid.domain.exception.CovidDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenDataRestClient {

    private static final String OPEN_DATA_URL = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";

    private final RestTemplate restTemplate;

    public byte[] downloadCovidStatistics() {
        String uriString = UriComponentsBuilder
                .fromHttpUrl(OPEN_DATA_URL)
                .toUriString();

        HttpEntity requestHttpEntity = new HttpEntity(createHeaders());

        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                requestHttpEntity,
                byte[].class
        );

        log.debug("Response from open data server: {}", responseEntity);
        byte[] document = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> CovidDataException.missingCovidData());

        String currentDate = LocalDate.now().toString();
        String csvFileName = "covid-" + currentDate + ".csv";
        log.info("Write statistics to file: {}", csvFileName);
        try {
            Files.write(Paths.get(csvFileName), document);
        } catch (IOException e) {
            log.error("Exception when writing covid data to CSV file: {}", csvFileName);
        }

        return document;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        return headers;
    }
}
