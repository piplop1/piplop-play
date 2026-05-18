package com.piplop.play.web.controller;

import com.piplop.play.domain.dto.*;
import com.piplop.play.domain.services.MovieService;
import com.piplop.play.domain.services.PiplopPlayAiService;
import com.piplop.play.web.exception.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/movies")
@Tag(name = "Movies", description = "Endpoints para la gestión de películas en PiplopPlay")
public class MovieController {
    private final MovieService movieService;
    private final PiplopPlayAiService aiService;

    public MovieController(MovieService movieService, PiplopPlayAiService aiService) {
        this.movieService = movieService;
        this.aiService = aiService;
    }

    @Operation(summary = "Obtener todas las películas", description = "Retorna la lista completa de películas registradas en la plataforma")
    @ApiResponse(responseCode = "200", description = "Lista de películas obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = MovieDto.class)))
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.ok(this.movieService.getAll());
    }

    @Operation(summary = "Obtener película por ID", description = "Retorna una película específica según su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película encontrada",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Película no encontrada",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getById(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable @Positive long id) {
        return ResponseEntity.ok(this.movieService.getById(id));
    }

    @Operation(summary = "Sugerir películas con IA", description = "Genera recomendaciones de películas personalizadas según las preferencias del usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sugerencias generadas exitosamente",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Body de la petición inválido",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/suggest")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestRequestDto suggestRequestDto) {
        return ResponseEntity.ok(this.aiService.generateMoviesSuggestion(suggestRequestDto.userPreferences()));
    }

    @Operation(summary = "Crear una película", description = "Registra una nueva película en la plataforma")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Película creada exitosamente",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la película inválidos o título duplicado",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    public ResponseEntity<MovieDto> add(@RequestBody @Valid CreateMovieDto createMovieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.movieService.add(createMovieDto));
    }

    @Operation(summary = "Actualización parcial de una película", description = "Actualiza uno o más campos de una película sin necesidad de enviar todos los campos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película actualizada exitosamente",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Película no encontrada",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MovieDto> patch(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable @Positive(message = "El id debe ser mayor que 0") long id,
            @RequestBody @Valid PatchMovieDto patchMovieDto) {
        return ResponseEntity.ok(this.movieService.patch(id, patchMovieDto));
    }

    @Operation(summary = "Reemplazar una película", description = "Reemplaza todos los campos de una película existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Película reemplazada exitosamente",
                    content = @Content(schema = @Schema(implementation = MovieDto.class))),
            @ApiResponse(responseCode = "404", description = "Película no encontrada",
                    content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o faltantes",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable @Positive(message = "El id debe ser mayor que 0") long id,
            @RequestBody @Valid UpdateMovieDto updateMovieDto) {
        return ResponseEntity.ok(this.movieService.update(id, updateMovieDto));
    }

    @Operation(summary = "Eliminar una película", description = "Elimina permanentemente una película de la plataforma")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Película eliminada exitosamente",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Película no encontrada",
                    content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la película", example = "1")
            @PathVariable @Positive long id) {
        this.movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}