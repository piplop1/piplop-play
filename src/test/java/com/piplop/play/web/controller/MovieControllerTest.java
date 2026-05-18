package com.piplop.play.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piplop.play.domain.Genre;
import com.piplop.play.domain.Status;
import com.piplop.play.domain.dto.CreateMovieDto;
import com.piplop.play.domain.dto.MovieDto;
import com.piplop.play.domain.exceptions.MovieNotFoundException;
import com.piplop.play.domain.services.MovieService;
import com.piplop.play.domain.services.PiplopPlayAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private PiplopPlayAiService aiService;

    private MovieDto movieDto;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        movieDto = new MovieDto(1L, "Inception", 148, Genre.SCI_FI, LocalDate.of(2010, 7, 16), 4.8, Status.AVAILABLE);
    }

    @Test
    void getAll_returnsOkWithList() throws Exception {
        when(movieService.getAll()).thenReturn(List.of(movieDto));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Inception"))
                .andExpect(jsonPath("$[0].genre").value("SCI_FI"));
    }

    @Test
    void getById_existingId_returnsOk() throws Exception {
        when(movieService.getById(1L)).thenReturn(movieDto);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.genre").value("SCI_FI"));
    }

    @Test
    void getById_nonExistingId_returnsNotFound() throws Exception {
        when(movieService.getById(99L)).thenThrow(new MovieNotFoundException(99L));

        mockMvc.perform(get("/movies/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void add_validDto_returnsCreated() throws Exception {
        CreateMovieDto createMovieDto = new CreateMovieDto(
                "Inception", 148, Genre.SCI_FI, LocalDate.of(2010, 7, 16), 4.8
        );
        when(movieService.add(any(CreateMovieDto.class))).thenReturn(movieDto);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMovieDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    void add_invalidDto_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_existingId_returnsNoContent() throws Exception {
        doNothing().when(movieService).delete(1L);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_nonExistingId_returnsNotFound() throws Exception {
        doThrow(new MovieNotFoundException(99L)).when(movieService).delete(99L);

        mockMvc.perform(delete("/movies/99"))
                .andExpect(status().isNotFound());
    }
}