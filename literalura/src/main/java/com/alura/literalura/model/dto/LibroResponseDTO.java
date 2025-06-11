package com.alura.literalura.model.dto;

import com.alura.literalura.model.Libro;

public record LibroResponseDTO (
    Long libroIdGutendex,
    String titulo,
    String autor,
    String idioma,
    Long numeroDescargas
)
{
    public static LibroResponseDTO fromEntity(Libro libro) {
        return new LibroResponseDTO(
                libro.getLibroIdGutendex(),
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma(),
                libro.getNumeroDescargas()
        );
    }

    @Override
    public String toString(){
        return String.format("IdGutendex: %d. Titulo: %s. Idioma: %s. Autor: %s. Descargas:%d%n", libroIdGutendex, titulo, idioma, autor, numeroDescargas);
    }
}
