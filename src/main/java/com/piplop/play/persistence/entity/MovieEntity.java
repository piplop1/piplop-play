package com.piplop.play.persistence.entity;

import com.piplop.play.domain.Status;
import com.piplop.play.domain.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "piplop_play_peliculas")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String titulo;

    @Column(nullable = false, precision = 3)
    private Integer duracion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Genre genero;

    @Column(precision = 3, scale = 2)
    private BigDecimal clasificacion;

    @Column(name = "fecha_estreno")
    private LocalDate fechaEstreno;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private Status estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Genre getGenero() {return genero;}

    public void setGenero(Genre genero) {this.genero = genero;}

    public BigDecimal getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(BigDecimal clasificacion) {
        this.clasificacion = clasificacion;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public Status getEstado() {return estado;}

    public void setEstado(Status estado) {this.estado = estado;}
}
