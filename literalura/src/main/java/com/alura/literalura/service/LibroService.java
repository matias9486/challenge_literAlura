package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.model.dto.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public LibroResponseDTO agregarLibro(LibroDTO libro){
        String idioma = !libro.idiomas().isEmpty()? libro.idiomas().get(0):"Desconocido";
        AutorDTO autorDto = libro.autores().isEmpty()? (new AutorDTO("Desconocido", 0, 0)) : libro.autores().get(0);

        //Si existe devuelvo el libro ya guardado
        Optional<Libro> libroAgregado = libroRepository.findByLibroIdGutendex(libro.libroIdGutendex());
        if(libroAgregado.isPresent())
            return LibroResponseDTO.fromEntity(libroAgregado.get());

        //Sino existe libro lo Guardo
        //guardo u obtengo autor
        Autor autor = autor = autorRepository.findByNombre(autorDto.nombre())
                .orElseGet( () -> autorRepository.save( new Autor(autorDto.nombre(), autorDto.anioNacimiento(), autorDto.anioFallecimiento(), new ArrayList<>()) ));

        Libro nuevoLibro = new Libro(libro.libroIdGutendex(),libro.titulo(), autor, idioma ,libro.numeroDescargas());
        autor.agregarLibro(nuevoLibro);

        return LibroResponseDTO.fromEntity(libroRepository.save(nuevoLibro));
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> buscarLibrosPorIdioma(String idioma){
        return libroRepository.findByIdiomaIgnoreCase(idioma).stream()
                .sorted()
                .map(l -> LibroResponseDTO.fromEntity(l))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<LibroBusquedaResponseDTO> obtenerLibrosConMasDeUnaBusqueda(){
        return libroRepository.findByBusquedasJPQL().stream()
                .sorted()
                .map(l -> new LibroBusquedaResponseDTO(l.getTitulo(), l.getBusquedas().stream().map(b -> b.getTerminoBuscar()).collect(Collectors.toList()), l.getBusquedas().size()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> obtenerTop10porDescargas(){
        return libroRepository.findTop10ByOrderByNumeroDescargasDesc().stream()
                .sorted()
                .map(l -> LibroResponseDTO.fromEntity(l))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> obtenerTop10porDescargasJPQL(){
        Pageable topTen = PageRequest.of(0, 10);
        return libroRepository.findTop10ByOrderByNumeroDescargasDescJPQL(topTen).stream()
                .sorted()
                .map(l -> LibroResponseDTO.fromEntity(l))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LibroResponseDTO> obtenerTodos(){
        return libroRepository.findAll().stream()
                .sorted()
                .map(l -> LibroResponseDTO.fromEntity(l))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstadisticaResponseDTO> obtenerEstadisticasPorIdiomaJPQL(){
        return libroRepository.obtenerEstadisticasPorIdiomaJPQL().stream()
                .sorted((o1, o2) -> o1.idioma().compareTo(o2.idioma()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IEstadisticaResponseDTO> obtenerEstadisticasPorIdiomaNativoUsandoInterfaces(){
        return libroRepository.obtenerEstadisticasPorIdiomaNativoUsandoInterfaces().stream()
                .sorted((o1, o2) -> o1.getIdioma().compareTo(o2.getIdioma()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EstadisticaNativeResponseDTO> obtenerEstadisticasPorIdiomaNativoUsandoClases(){
        return libroRepository.obtenerEstadisticasPorIdiomaNativoUsandoClases().stream()
                .sorted((o1, o2) -> o1.getIdioma().compareTo(o2.getIdioma()))
                .collect(Collectors.toList());
    }
}
