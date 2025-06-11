package com.alura.literalura.repository;

import com.alura.literalura.model.*;
import com.alura.literalura.model.dto.EstadisticaResponseDTO;
import com.alura.literalura.model.dto.EstadisticaNativeResponseDTO;
import com.alura.literalura.model.dto.IEstadisticaResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByLibroIdGutendex(Long libroIdGutendex);
    List<Libro> findByIdiomaIgnoreCase(String idioma);

    List<Libro> findTop10ByOrderByNumeroDescargasDesc();


    @Query(value = """
        SELECT l
        FROM Libro l
        WHERE SIZE(l.busquedas) > 1
        """)
    List<Libro> findByBusquedasJPQL();

    //JPQL no tiene limit, limito los resultados con el paginable
    @Query(value = """
        SELECT l FROM Libro l 
        ORDER BY l.numeroDescargas DESC                              
        """)
    List<Libro> findTop10ByOrderByNumeroDescargasDescJPQL(Pageable pageable);

    /* Proyeccion usando JPQL y constructor
    Spring puede mapear automáticamente los resultados si usás una clase/record DTO con un constructor que coincida exactamente con el orden de los campos seleccionados.

    Al usar JPQL y usar objetos en vez de tablas puedo usar el constructor de la clase para instanciar la consulta
    pero debo referenciar con el nombre del paquete
     */
    @Query(value = """
    SELECT 
        new com.alura.literalura.model.dto.EstadisticaResponseDTO(
            l.idioma, 
            COUNT(l), 
            AVG(l.numeroDescargas), 
            MIN(l.numeroDescargas),
            MAX(l.numeroDescargas),
            SUM(l.numeroDescargas)
        )
    FROM Libro l 
    GROUP BY l.idioma
    """)
    List<EstadisticaResponseDTO> obtenerEstadisticasPorIdiomaJPQL();

    /*proyeccion usando consulta nativa e interfaces.
    Si usás Spring Boot 3.x + Hibernate 6.x, podrías usar interfaces como proyección nativa con alias que coincidan
    solo puedo acceder a los datos usando los getters definidos en la interface
    */
    @NativeQuery("""
        SELECT l.idioma AS idioma, 
               COUNT(*) AS cantidad, 
               AVG(l.numero_descargas) AS promedioDescargas, 
               MIN(l.numero_descargas) AS minimoDescargas,
               MAX(l.numero_descargas) AS maximoDescargas,
               SUM(l.numero_descargas) AS totalDescargas
        FROM libro l 
        GROUP BY l.idioma
    """)
    List<IEstadisticaResponseDTO> obtenerEstadisticasPorIdiomaNativoUsandoInterfaces();

    /* proyecciones usando Consulta nativa y clases
    - Crear un DTO
    - Mapear los resultados a ese DTO usando @SqlResultSetMapping. Ver EstadisticaNativeResponseDTO
    - La anotación @SqlResultSetMapping debe ir sobre una clase que esté anotada con @Entity, porque JPA solo la reconoce en entidades mapeadas.
    - El nombre del resultSetMapping debe coincidir exactamente con el que se declara en @SqlResultSetMapping
    - Crear la consulta nativa que lo utilice
    */
    @NativeQuery( value = """
        SELECT l.idioma, 
               COUNT(*) as cantidad, 
               AVG(l.numero_descargas) as promedioDescargas, 
               MIN(l.numero_descargas) as minimoDescargas,
               MAX(l.numero_descargas) as maximoDescargas,
               SUM(l.numero_descargas) as totalDescargas
        FROM libro l 
        GROUP BY l.idioma
        """, sqlResultSetMapping = "EstadisticaIdiomaMapping")
    List<EstadisticaNativeResponseDTO> obtenerEstadisticasPorIdiomaNativoUsandoClases();
}