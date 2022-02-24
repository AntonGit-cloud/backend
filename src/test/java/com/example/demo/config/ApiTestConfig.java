package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Configuration
public class ApiTestConfig {

    @Bean
    @Primary
    public Clock fixedClock() {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedFixedDateTime = LocalDate.of(2021, 1, 9).atStartOfDay(zone);
        return Clock.fixed(zonedFixedDateTime.toInstant(), zone);
    }
}
