package com.alura.literalura.model.dto;

import com.alura.literalura.model.Autor;

import java.util.List;
import java.util.stream.Collectors;

public record AutorResponseDTO(
        String nombre,
        int anioNacimiento,
        int anioFallecimiento,
        List<LibroResponseDTO> libros
) {
    public static AutorResponseDTO fromEntity(Autor autor) {
        return new AutorResponseDTO(
                autor.getNombre(),
                autor.getAnioNacimiento(),
                autor.getAnioFallecimiento(),
                autor.getLibros().stream().map(l -> LibroResponseDTO.fromEntity(l)).collect(Collectors.toList())
                );
    }

    @Override
    public String toString(){
        return String.format("%s(%d-%d). Libros Escritos: %d", nombre, anioNacimiento, anioFallecimiento, libros.stream().count());
    }
}
