package com.alura.literalura.model.dto;

import com.alura.literalura.model.Busqueda;

import java.util.Set;
import java.util.stream.Collectors;

public record BusquedaResponseDTO(
        String terminoBusqueda,
        Set<LibroResponseDTO> libros
) {
    public static BusquedaResponseDTO fromEntity(Busqueda busqueda){
        return new BusquedaResponseDTO(busqueda.getTerminoBuscar(),
                busqueda.getLibros().stream()
                        .map(l -> LibroResponseDTO.fromEntity(l))
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public String toString(){
        return String.format(
                "Término a buscar: %s\nResultado de búsqueda:%s",
                terminoBusqueda,
                libros.stream()
                        .map(l -> "\n\t" + l.titulo())
                        .collect(Collectors.joining())
        );
    }
}
