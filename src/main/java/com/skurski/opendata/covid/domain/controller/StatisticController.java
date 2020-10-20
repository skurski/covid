package com.skurski.opendata.covid.domain.controller;

import com.skurski.opendata.covid.domain.entity.Statistic;
import com.skurski.opendata.covid.domain.exception.StatisticException;
import com.skurski.opendata.covid.domain.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping(value = "/{date}/{country}")
    @Transactional(readOnly = true)
    public ResponseEntity<Statistic> getStatisticOf(@PathVariable String date,
                                                    @PathVariable String country) {
        return statisticService.getByDateAndCountry(date, country)
                .map(statistic -> {
                    log.info("Fetched statistic for {} on day {}", country, date);
                    return createSuccessfulResponse(statistic);
                })
                .orElseThrow(() -> StatisticException.missingData(date, country));
    }

    private <T> ResponseEntity<T> createSuccessfulResponse(T body) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
