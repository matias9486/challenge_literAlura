package com.alura.literalura.repository;

import com.alura.literalura.model.Busqueda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusquedaRepository extends JpaRepository<Busqueda, Long> {
}
