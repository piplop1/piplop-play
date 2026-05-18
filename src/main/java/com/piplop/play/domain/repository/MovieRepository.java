package com.piplop.play.domain.repository;

import com.piplop.play.domain.dto.*;

import java.util.List;

public interface MovieRepository {
    List<MovieDto> getAll();
    MovieDto getById(long id);
    MovieDto save(CreateMovieDto createMovieDto);
    MovieDto patch(long id, PatchMovieDto patchMovieDto);
    MovieDto update(long id, UpdateMovieDto updateMovieDto);
    void delete(long id);
}
