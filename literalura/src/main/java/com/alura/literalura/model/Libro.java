package com.alura.literalura.model;

import com.alura.literalura.model.dto.EstadisticaNativeResponseDTO;
import jakarta.persistence.*;

import java.util.Set;

@Entity

@SqlResultSetMapping(
        name = "EstadisticaIdiomaMapping",
        classes = @ConstructorResult(
                targetClass = EstadisticaNativeResponseDTO.class,
                columns = {
                        @ColumnResult(name = "idioma", type = String.class),
                        @ColumnResult(name = "cantidad", type = Long.class),
                        @ColumnResult(name = "promedioDescargas", type = Double.class),
                        @ColumnResult(name = "minimoDescargas", type = Long.class),
                        @ColumnResult(name = "maximoDescargas", type = Long.class),
                        @ColumnResult(name = "totalDescargas", type = Long.class)
                }
        )
)
public class Libro implements Comparable<Libro>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long libroIdGutendex;
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
    private String idioma;
    private Long numeroDescargas;

    @ManyToMany(mappedBy = "libros", fetch = FetchType.EAGER)
    private Set<Busqueda> busquedas;

    public Libro(){}
    public Libro(Long libroIdGutendex, String titulo, Autor autor, String idioma,Long numeroDescargas) {
        this.libroIdGutendex = libroIdGutendex;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibroIdGutendex() {
        return libroIdGutendex;
    }

    public void setLibroIdGutendex(Long libroIdGutendex) {
        this.libroIdGutendex = libroIdGutendex;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Set<Busqueda> getBusquedas() {
        return busquedas;
    }

    public void setBusquedas(Set<Busqueda> busquedas) {
        this.busquedas = busquedas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", libroIdGutendex=" + libroIdGutendex +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor.getNombre() +
                ", idioma='" + idioma + '\'' +
                '}';
    }

    @Override
    public int compareTo(Libro o) {
        return this.getTitulo().compareTo(o.getTitulo());
    }
}
