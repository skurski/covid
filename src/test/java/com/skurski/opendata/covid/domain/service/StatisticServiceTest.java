package com.skurski.opendata.covid.domain.service;

import com.skurski.opendata.covid.domain.entity.Statistic;
import com.skurski.opendata.covid.domain.repository.StatisticRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StatisticServiceTest {

    @Mock
    private StatisticRepository statisticRepository;

    @InjectMocks
    private StatisticService statisticService;

    @Test
    void shouldLoadCsvFile() {
        List<Statistic> statistics = statisticService.loadStatistics();

        assertThat(statistics).isNotEmpty();
        assertThat(statistics.get(0).getDay()).isEqualTo(LocalDate.now().getDayOfMonth());
        assertThat(statistics.get(0).getMonth()).isEqualTo(LocalDate.now().getMonthValue());
        assertThat(statistics.get(0).getYear()).isEqualTo(LocalDate.now().getYear());
    }
}