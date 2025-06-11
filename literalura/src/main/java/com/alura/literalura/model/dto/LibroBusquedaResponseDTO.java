package com.alura.literalura.model.dto;

import java.util.List;
public class LibroBusquedaResponseDTO {
    private String titulo;
    private List<String> terminosBuscados;
    private Integer apariciones;

    public LibroBusquedaResponseDTO(String titulo, List<String> terminosBuscados, Integer apariciones) {
        this.titulo = titulo;
        this.terminosBuscados = terminosBuscados;
        this.apariciones = apariciones;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getTerminosBuscados() {
        return terminosBuscados;
    }

    public void setTerminosBuscados(List<String> terminosBuscados) {
        this.terminosBuscados = terminosBuscados;
    }

    public Integer getApariciones() {
        return apariciones;
    }

    public void setApariciones(Integer apariciones) {
        this.apariciones = apariciones;
    }

    @Override
    public String toString() {
        return "Titulo: %s. Terminos: %s. Apariciones: %d ".formatted(
                titulo, terminosBuscados, apariciones
        );
    }
}
