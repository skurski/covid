package com.skurski.opendata.covid.domain.service;

import com.skurski.opendata.covid.domain.entity.Statistic;
import com.skurski.opendata.covid.domain.exception.CovidDataException;
import com.skurski.opendata.covid.domain.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

    private static final String CSV_COVID_FILE_NAME_TEMPLATE = "covid-%s.csv";

    private final StatisticRepository statisticRepository;

    public List<Statistic> loadStatistics() {
        String fileName = String.format(CSV_COVID_FILE_NAME_TEMPLATE, LocalDate.now());

        List<Statistic> covidPolandStatistics;
        try {
            covidPolandStatistics = Files.lines(Paths.get(fileName))
                    .map(line -> line.split(","))
                    .filter(record -> record[6].equalsIgnoreCase("Poland"))
                    .map(record -> Statistic.builder()
                                .dateRep(LocalDate.parse(record[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                                .day(Integer.parseInt(record[1]))
                                .month(Integer.parseInt(record[2]))
                                .year(Integer.parseInt(record[3]))
                                .cases(Integer.parseInt(record[4]))
                                .deaths(Integer.parseInt(record[5]))
                                .country(record[6])
                                .getId(record[7])
                                .countryCode(record[8])
                                .popData2019(Integer.parseInt(record[9]))
                                .continentExp(record[10])
                                .build()
                    )
                    .collect(Collectors.toList());

            statisticRepository.saveAll(covidPolandStatistics);

        } catch (IOException e) {
            log.error("Error occured when processing csv file: {}", fileName);
            e.printStackTrace();
            throw CovidDataException.errorWhileProcessingCovidFile();
        }

        return covidPolandStatistics;
    }

    public Optional<Statistic> getByDateAndCountry(String date, String country) {
        return statisticRepository.findByDateRepAndCountry(LocalDate.parse(date), country);
    }
}
