package com.skurski.opendata.covid.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime creationDate;

    @Column(name = "date_rep", nullable = false)
    private LocalDate dateRep;

    private int day;

    private int month;

    private int year;

    private int cases;

    private int deaths;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "get_id", nullable = false)
    private String getId;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "pop_data_2019", nullable = false)
    private int popData2019;

    @Column(name = "continent_exp", nullable = false)
    private String continentExp;

}