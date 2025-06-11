package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    // Buscar autores que contengan cierta parte del nombre (como un LIKE %string%) sin importar mayúsculas
    List<Autor> findByNombreContainingIgnoreCase(String nombreParcial);
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Long anioNacimiento, Long anioFallecimiento);

    /* Tipos de parametros en consultas JPQL y Nativas:
    - Parámetros nombrados (:nombre), puedo definir el parametro usando @Param. No requiere orden de parametros. Mayor legibilidad y seguridad.
    - Parámetros posicionales (?1, ?2, etc.). Requiere orden de parametros, usa ?1 solo si es una consulta muy corta o tienes una sola variable.
    * */
    @Query("select a from Autor a where :anio between a.anioNacimiento and a.anioFallecimiento")
    //List<Autor> findByAnioJPQL(@Param("anio") Long anio);
    List<Autor> findByAnioJPQL(Long anio);
    @NativeQuery("select * from autor where anio_nacimiento <= ?1 and anio_fallecimiento >=?1")
    List<Autor> findByAnioNative(Long anio);
}
