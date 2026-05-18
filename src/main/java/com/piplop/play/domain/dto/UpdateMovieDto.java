package com.piplop.play.domain.dto;

import com.piplop.play.domain.Genre;
import com.piplop.play.domain.Status;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UpdateMovieDto(
        @NotBlank(message = "El título no puede estar vacío")
        String title,

        @NotNull(message = "La duración es obligatoria")
        @Positive(message = "La duración debe ser mayor que 0")
        @Max(value = 51420, message = "¡La duración es mayor que la película más larga del mundo!")
        Integer duration,

        @NotNull(message = "El género es obligatorio")
        Genre genre,

        @NotNull(message = "La fecha de estreno es obligatoria")
        @PastOrPresent(message = "La fecha de estreno no puede ser futura")
        LocalDate releaseDate,

        @NotNull(message = "El rating es obligatorio")
        @DecimalMin(value = "0.0", message = "El rating no puede ser menor que 0")
        @DecimalMax(value = "5.0", message = "El rating no puede ser mayor que 5")
        Double rating,

        @NotNull(message = "El status es obligatorio")
        Status status
) {
}
