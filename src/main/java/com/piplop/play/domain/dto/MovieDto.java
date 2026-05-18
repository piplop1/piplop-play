package com.piplop.play.domain.dto;

import com.piplop.play.domain.Genre;
import com.piplop.play.domain.Status;

import java.time.LocalDate;

public record MovieDto(
        Long id,
        String title,
        Integer duration,
        Genre genre,
        LocalDate releaseDate,
        Double rating,
        Status status
) {
}