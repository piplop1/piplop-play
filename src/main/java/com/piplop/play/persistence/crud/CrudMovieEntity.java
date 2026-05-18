package com.piplop.play.persistence.crud;

import com.piplop.play.persistence.entity.MovieEntity;
import org.springframework.data.repository.CrudRepository;

public interface CrudMovieEntity extends CrudRepository<MovieEntity, Long> {
    MovieEntity findFirstByTituloIgnoreCase(String titulo);
}
