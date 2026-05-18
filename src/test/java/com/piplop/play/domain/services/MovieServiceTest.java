package com.piplop.play.domain.services;

import com.piplop.play.domain.dto.MovieDto;
import com.piplop.play.domain.exceptions.MovieNotFoundException;
import com.piplop.play.domain.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private MovieDto movieDto;

    @BeforeEach
    void setUp() {
        movieDto = new MovieDto(1L, "Inception", 148, null, null, null, null);
    }

    @Test
    void getById_existingId_returnsMovieDto() {
        when(movieRepository.getById(1L)).thenReturn(movieDto);

        MovieDto result = movieService.getById(1L);

        assertNotNull(result);
        assertEquals("Inception", result.title());
        verify(movieRepository, times(1)).getById(1L);
    }

    @Test
    void getById_nonExistingId_throwsMovieNotFoundException() {
        when(movieRepository.getById(99L)).thenThrow(new MovieNotFoundException(99L));

        assertThrows(MovieNotFoundException.class, () -> movieService.getById(99L));
        verify(movieRepository, times(1)).getById(99L);
    }

    @Test
    void getAll_returnsListOfMovies() {
        when(movieRepository.getAll()).thenReturn(List.of(movieDto));

        List<MovieDto> result = movieService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).title());
        verify(movieRepository, times(1)).getAll();
    }
}