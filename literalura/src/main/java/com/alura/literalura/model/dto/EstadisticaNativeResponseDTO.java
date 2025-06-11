package com.alura.literalura.model.dto;

public class EstadisticaNativeResponseDTO {
    private String idioma;
    private Long cantidad;
    private Double promedioDescargas;
    private Long minimoDescargas;
    private Long maximoDescargas;
    private Long totalDescargas;

    public EstadisticaNativeResponseDTO(){}
    public EstadisticaNativeResponseDTO(String idioma, Long cantidad, Double promedioDescargas, Long minimoDescargas, Long maximoDescargas, Long totalDescargas) {
        this.idioma = idioma;
        this.cantidad = cantidad;
        this.promedioDescargas = promedioDescargas;
        this.minimoDescargas = minimoDescargas;
        this.maximoDescargas = maximoDescargas;
        this.totalDescargas = totalDescargas;
    }

    public String getIdioma() {
        return idioma;
    }
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    public Long getCantidad() {
        return cantidad;
    }
    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
    public Double getPromedioDescargas() {
        return promedioDescargas;
    }
    public void setPromedioDescargas(Double promedioDescargas) {
        this.promedioDescargas = promedioDescargas;
    }
    public Long getMinimoDescargas() {
        return minimoDescargas;
    }
    public void setMinimoDescargas(Long minimoDescargas) {
        this.minimoDescargas = minimoDescargas;
    }
    public Long getMaximoDescargas() {
        return maximoDescargas;
    }
    public void setMaximoDescargas(Long maximoDescargas) {
        this.maximoDescargas = maximoDescargas;
    }
    public Long getTotalDescargas() {
        return totalDescargas;
    }
    public void setTotalDescargas(Long totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    @Override
    public String toString() {
        return "Idioma: %s | Cantidad: %d | Descargas(Promedio: %.2f | Mín: %d | Máx: %d | Total: %d)".formatted(
            this.getIdioma(),
            this.getCantidad(),
            this.getPromedioDescargas(),
            this.getMinimoDescargas(),
            this.getMaximoDescargas(),
            this.getTotalDescargas()
        );
    }
}
