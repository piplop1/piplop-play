package com.piplop.play.domain.dto;

import com.piplop.play.domain.Genre;
import com.piplop.play.domain.Status;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatchMovieDto(
        @Size(min = 1, max = 255, message = "El título no puede estar vacío")
        String title,

        @Positive(message = "La duración debe ser mayor que 0")
        @Max(value = 51420, message = "¡La duración es mayor que la película más larga del mundo!")
        Integer duration,

        Genre genre,

        @PastOrPresent(message = "La fecha de lanzamiento debe ser anterior a la fecha actual")
        LocalDate releaseDate,

        @DecimalMin(value = "0.0", message = "El rating no puede ser menor que 0")
        @DecimalMax(value = "5.0", message = "El rating no puede ser mayor que 5")
        Double rating,

        Status status
) {
}
