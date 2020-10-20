package com.skurski.opendata.covid.domain.repository;

import com.skurski.opendata.covid.domain.entity.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Long> {

    Optional<Statistic> findByDateRepAndCountry(LocalDate date, String country);
}
