package com.alura.literalura.service;

import com.alura.literalura.model.*;
import com.alura.literalura.model.dto.AutorDTO;
import com.alura.literalura.model.dto.BusquedaResponseDTO;
import com.alura.literalura.model.dto.LibroDTO;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.BusquedaRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusquedaService {
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private BusquedaRepository busquedaRepository;

    @Transactional
    public BusquedaResponseDTO guardarBusquedaConLibros(String terminoBuscar, List<LibroDTO> librosDTO) {
        Set<Libro> librosGuardados = new HashSet<>();
        Libro libro = null;
        Autor autor;

        for (LibroDTO libroDTO : librosDTO) {
            String idioma = !libroDTO.idiomas().isEmpty()? libroDTO.idiomas().get(0):"Desconocido";
            AutorDTO autorDto = libroDTO.autores().isEmpty()? (new AutorDTO("Desconocido", 0, 0)) : libroDTO.autores().get(0);

            //Si existe devuelvo el libro ya guardado
            Optional<Libro> libroAgregado = libroRepository.findByLibroIdGutendex(libroDTO.libroIdGutendex());
            if(libroAgregado.isPresent())
                libro = libroAgregado.get();
            else {
                //Sino existe libro lo Guardo
                //guardo u obtengo autor
                autor = autorRepository.findByNombre(autorDto.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(autorDto.nombre(), autorDto.anioNacimiento(), autorDto.anioFallecimiento(), new ArrayList<>())));

                libro = new Libro(libroDTO.libroIdGutendex(), libroDTO.titulo(), autor, idioma, libroDTO.numeroDescargas());
                autor.agregarLibro(libro);
                libro = libroRepository.save(libro); // guardar libro
            }
            librosGuardados.add(libro);
        }

        // Crear búsqueda y asociar libros
        Busqueda busqueda = new Busqueda();
        busqueda.setTerminoBuscar(terminoBuscar);
        busqueda.setLibros(librosGuardados);

        return BusquedaResponseDTO.fromEntity(busquedaRepository.save(busqueda));
    }

    @Transactional(readOnly = true)
    public List<BusquedaResponseDTO> obtenerTodasBusquedas(){
        return busquedaRepository.findAll().stream()
                .map(b -> BusquedaResponseDTO.fromEntity(b))
                .collect(Collectors.toList());
    }
}
