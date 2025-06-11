package com.alura.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AutorDTO(
        @JsonAlias("name")String nombre,
        @JsonAlias("birth_year")Integer anioNacimiento,
        @JsonAlias("death_year")Integer anioFallecimiento
        ) {

}
