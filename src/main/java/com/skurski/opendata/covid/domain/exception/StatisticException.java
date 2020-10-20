package com.skurski.opendata.covid.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StatisticException extends ResponseStatusException {

    private static final String MISSING_DATA_MESSAGE = "Could not find Covid Data for country %s and date %s";

    private StatisticException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public static StatisticException missingData(String date, String country) {
        String message = String.format(MISSING_DATA_MESSAGE, country, date);
        return new StatisticException(HttpStatus.NOT_FOUND, message);
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}
