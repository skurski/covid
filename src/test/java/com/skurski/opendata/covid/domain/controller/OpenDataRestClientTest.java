package com.skurski.opendata.covid.domain.controller;

import com.skurski.opendata.covid.domain.exception.CovidDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenDataRestClientTest {

    private static final String CSV_DATA_CONTENT = "Test; content; document";
    private static final String OPEN_DATA_ENDPOINT = "https://opendata.ecdc.europa.eu/covid19/casedistribution/csv";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OpenDataRestClient openDataRestClient;

    @Test
    void shouldDownloadDocument() {
        byte[] content = CSV_DATA_CONTENT.getBytes();

        when(restTemplate.exchange(
                eq(OPEN_DATA_ENDPOINT),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)))
                .thenReturn(ResponseEntity.ok(content));

        byte[] document = openDataRestClient.downloadCovidStatistics();

        assertThat(document).isEqualTo(content);
    }

    @Test
    void shouldThrowExceptionWhenResponseIsEmpty() {
        when(restTemplate.exchange(
                eq(OPEN_DATA_ENDPOINT),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(byte[].class)))
                .thenReturn(ResponseEntity.ok(null));

        Throwable thrown = catchThrowable(() -> openDataRestClient.downloadCovidStatistics());

        assertThat(thrown).isInstanceOf(CovidDataException.class).hasNoCause();
    }
}