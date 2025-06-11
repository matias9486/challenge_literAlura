package com.alura.literalura.model.dto;

//Si usás Spring Boot 3.x + Hibernate 6.x, podrías usar interfaces como proyección nativa con alias que coincidan:
public interface IEstadisticaResponseDTO {
    String getIdioma();
    Long getCantidad();
    Double getPromedioDescargas();
    Long getMinimoDescargas();
    Long getMaximoDescargas();
    Long getTotalDescargas();
}
