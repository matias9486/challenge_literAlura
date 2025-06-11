package com.alura.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("id")Long libroIdGutendex,
        @JsonAlias("title")String titulo,
        @JsonAlias("authors")List<AutorDTO> autores, //(es una lista pero tomaremos el primer autor,
        @JsonAlias("languages")
        List<String> idiomas,//(es una lista pero manejaremos el primero),
        @JsonAlias("download_count")Long numeroDescargas
) {
        @Override
        public String toString(){
                return String.format("IdGutendex: %d. Titulo: %s. Idioma: %s. Autor: %s. Descargas:%d%n", libroIdGutendex, titulo, idiomas.isEmpty()?"Desconodio":idiomas.get(0), autores.isEmpty()?"Desconocido":autores.get(0), numeroDescargas);
        }
}
