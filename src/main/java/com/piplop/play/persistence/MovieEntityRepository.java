package com.piplop.play.persistence;

import com.piplop.play.domain.Status;
import com.piplop.play.domain.dto.*;
import com.piplop.play.domain.exceptions.MovieAlreadyExistsException;
import com.piplop.play.domain.exceptions.MovieNotFoundException;
import com.piplop.play.domain.repository.MovieRepository;
import com.piplop.play.persistence.crud.CrudMovieEntity;
import com.piplop.play.persistence.entity.MovieEntity;
import com.piplop.play.persistence.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieEntityRepository implements MovieRepository {
    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper) {
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDto> getAll() {
        return this.movieMapper.toDto(crudMovieEntity.findAll());
    }

    @Override
    public MovieDto getById(long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        return this.movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieDto save(CreateMovieDto createMovieDto) {
        if (this.crudMovieEntity.findFirstByTituloIgnoreCase(createMovieDto.title()) != null) {
            throw new MovieAlreadyExistsException(createMovieDto.title());
        }

        MovieEntity movieEntity = this.movieMapper.toEntity(createMovieDto);
        movieEntity.setEstado(Status.AVAILABLE);

        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto patch(long id, PatchMovieDto patchMovieDto) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElseThrow(() -> new MovieNotFoundException(id));
        if (patchMovieDto.title() != null && !patchMovieDto.title().equalsIgnoreCase(movieEntity.getTitulo())) {
            MovieEntity existingMovie = this.crudMovieEntity.findFirstByTituloIgnoreCase(patchMovieDto.title());
            if (existingMovie != null) {
                throw new MovieAlreadyExistsException(patchMovieDto.title());
            }
        }

        this.movieMapper.patchEntityFromDto(patchMovieDto, movieEntity);
        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDto update(long id, UpdateMovieDto updateMovieDto) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        MovieEntity existingMovie = this.crudMovieEntity
                .findFirstByTituloIgnoreCase(updateMovieDto.title());

        if (existingMovie != null && !existingMovie.getId().equals(id)) {
            throw new MovieAlreadyExistsException(updateMovieDto.title());
        }

        this.movieMapper.updateEntityFromDto(updateMovieDto, movieEntity);
        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public void delete(long id) {
        if(!this.crudMovieEntity.existsById(id)){
            throw new MovieNotFoundException(id);
        }

        this.crudMovieEntity.deleteById(id);
    }
}
