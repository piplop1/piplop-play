package com.piplop.play.domain.exceptions;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException(String movieTitle) {
        super("La pelicula " + movieTitle + " ya existe");
    }
}
