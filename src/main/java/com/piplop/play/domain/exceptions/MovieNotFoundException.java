package com.piplop.play.domain.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(Long id) {
        super("La película con id " + id + " no existe");
    }
}