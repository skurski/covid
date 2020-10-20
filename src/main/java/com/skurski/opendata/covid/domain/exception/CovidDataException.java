package com.skurski.opendata.covid.domain.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
public class CovidDataException extends ResponseStatusException {

    private static final String MISSING_DATA_MESSAGE = "Could not find Covid Data on Open Data Server using Rest API";
    private static final String PROCESSING_ERROR_MSG = "Error occurred when processing CSV file with covid data.";

    private CovidDataException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static CovidDataException missingCovidData() {
        String message = String.format(MISSING_DATA_MESSAGE);
        return new CovidDataException(HttpStatus.NOT_FOUND, message);
    }

    public static CovidDataException errorWhileProcessingCovidFile() {
        String message = String.format(PROCESSING_ERROR_MSG);
        return new CovidDataException(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}
