package com.piplop.play.domain.services;

import com.piplop.play.domain.dto.*;
import com.piplop.play.domain.repository.MovieRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Tool("Busca todas las películas que existan dentro de la plataforma.")
    public List<MovieDto> getAll() {
        return this.movieRepository.getAll();
    }

    public MovieDto getById(long id) {
        return this.movieRepository.getById(id);
    }

    public MovieDto add(CreateMovieDto createMovieDto) {
        return this.movieRepository.save(createMovieDto);
    }

    public MovieDto patch(long id, PatchMovieDto patchMovieDto) {
        return this.movieRepository.patch(id, patchMovieDto);
    }

    public MovieDto update(long id, UpdateMovieDto updateMovieDto) {
        return this.movieRepository.update(id, updateMovieDto);
    }

    public void delete(long id) {
        this.movieRepository.delete(id);
    }
}
