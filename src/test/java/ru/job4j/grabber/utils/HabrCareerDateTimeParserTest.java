package ru.job4j.grabber.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.*;

class HabrCareerDateTimeParserTest {
   private final static HabrCareerDateTimeParser PARS = new HabrCareerDateTimeParser();
    @Test
    public void formatteDataTime1() {
        String parse = "2024-02-05T14:36:35+03:00";
        assertThat(PARS.parse(parse))
                .isEqualTo(LocalDateTime.of(2024, Month.FEBRUARY, 5, 14, 36, 35));
    }

    @Test
    public void formatteDataTime2() {
        String parse = "2024-02-06T21:30:01+03:00";
        assertThat(PARS.parse(parse))
                .isEqualTo(LocalDateTime.of(2024, Month.FEBRUARY, 6, 21, 30, 01));
    }
}