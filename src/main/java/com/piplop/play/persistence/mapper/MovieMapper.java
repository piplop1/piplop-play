package com.piplop.play.persistence.mapper;

import com.piplop.play.domain.dto.*;
import com.piplop.play.persistence.entity.MovieEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "duracion", target = "duration")
    @Mapping(source = "genero", target = "genre")
    @Mapping(source = "fechaEstreno", target = "releaseDate")
    @Mapping(source = "clasificacion", target = "rating")
    @Mapping(source = "estado", target = "status")
    MovieDto toDto(MovieEntity entity);

    List<MovieDto> toDto(Iterable<MovieEntity> entities);

    @InheritInverseConfiguration
    MovieEntity toEntity(MovieDto movieDto);

    @Mapping(source = "title", target = "titulo")
    @Mapping(source = "duration", target = "duracion")
    @Mapping(source = "genre", target = "genero")
    @Mapping(source = "releaseDate", target = "fechaEstreno")
    @Mapping(source = "rating", target = "clasificacion")
    MovieEntity toEntity(CreateMovieDto createMovieDto);

    @Mapping(target = "titulo", source = "title")
    @Mapping(target = "fechaEstreno", source = "releaseDate")
    @Mapping(target = "clasificacion", source = "rating")
    @Mapping(target = "estado", source = "status")
    void patchEntityFromDto(PatchMovieDto patchMovieDto, @MappingTarget MovieEntity movieEntity);

    @Mapping(target = "titulo", source = "title")
    @Mapping(target = "duracion", source = "duration")
    @Mapping(target = "genero", source = "genre")
    @Mapping(target = "fechaEstreno", source = "releaseDate")
    @Mapping(target = "clasificacion", source = "rating")
    @Mapping(target = "estado", source = "status")
    void updateEntityFromDto(UpdateMovieDto updateMovieDto, @MappingTarget MovieEntity movieEntity);
}
