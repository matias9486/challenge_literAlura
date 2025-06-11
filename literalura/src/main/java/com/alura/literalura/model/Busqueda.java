package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Busqueda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String terminoBuscar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "busqueda_libro",
            joinColumns = @JoinColumn(name = "busqueda_id"),
            inverseJoinColumns = @JoinColumn(name = "libro_id")
    )
    private Set<Libro> libros;

    public Busqueda(){
        this.libros = new HashSet<>();
    }

    public Busqueda(String terminoBuscar, Set<Libro> libros) {
        this.terminoBuscar = terminoBuscar;
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminoBuscar() {
        return terminoBuscar;
    }

    public void setTerminoBuscar(String terminoBuscar) {
        this.terminoBuscar = terminoBuscar;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }
}
