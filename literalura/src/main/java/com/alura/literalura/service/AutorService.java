package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.model.dto.AutorDTO;
import com.alura.literalura.model.dto.AutorResponseDTO;
import com.alura.literalura.model.dto.LibroDTO;
import com.alura.literalura.model.dto.LibroResponseDTO;
import com.alura.literalura.repository.AutorRepository;

import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public LibroResponseDTO agregarAutorConLibro(LibroDTO libro){
        String idioma = !libro.idiomas().isEmpty()? libro.idiomas().get(0):"Desconocido";
        AutorDTO autorDto = libro.autores().isEmpty()? (new AutorDTO("Desconocido", 0, 0)) : libro.autores().get(0);

        //obtengo autor o genero uno para guardar
        Autor autor = autorRepository.findByNombre(autorDto.nombre())
                .orElseGet( () ->  new Autor(autorDto.nombre(), autorDto.anioNacimiento(), autorDto.anioFallecimiento(), new ArrayList<>()) );

        //Busco libro, Si existe devuelvo el libro ya guardado
        Optional<Libro> libroAgregado = libroRepository.findByLibroIdGutendex(libro.libroIdGutendex());
        if(libroAgregado.isPresent())
            return LibroResponseDTO.fromEntity(libroAgregado.get());

        //Sino existe libro genero uno para guardar
        Libro nuevoLibro = new Libro(libro.libroIdGutendex(),libro.titulo(), autor, idioma ,libro.numeroDescargas());
        autor.agregarLibro(nuevoLibro);

        autor = autorRepository.save(autor);

        return LibroResponseDTO.fromEntity(nuevoLibro);

    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> obtenerAutores(){
        return autorRepository.findAll().stream()
                .sorted()
                .map(a -> AutorResponseDTO.fromEntity(a))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> obtenerAutoresPorNombre(String buscar){
        return autorRepository.findByNombreContainingIgnoreCase(buscar).stream()
                .sorted()
                .map(a -> AutorResponseDTO.fromEntity(a))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> autoresPorAnioDerivada(Long anioNacimiento, Long anioFallecimiento){
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anioNacimiento, anioFallecimiento).stream()
                .sorted()
                .map(a -> AutorResponseDTO.fromEntity(a))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> autoresPorAnioJPQL(Long anio){
        return autorRepository.findByAnioJPQL(anio).stream()
                .sorted()
                .map(a -> AutorResponseDTO.fromEntity(a))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<AutorResponseDTO> autoresPorAnioNative(Long anioNacimiento){
        return autorRepository.findByAnioNative(anioNacimiento).stream()
                .sorted()
                .map(a -> AutorResponseDTO.fromEntity(a))
                .collect(Collectors.toList());
    }
}
