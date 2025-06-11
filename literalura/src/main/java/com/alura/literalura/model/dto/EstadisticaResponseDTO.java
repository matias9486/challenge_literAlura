package com.alura.literalura.model.dto;

public record EstadisticaResponseDTO(
        String idioma,
        Long cantidad,
        Double promedioDescargas,
        Long minimoDescargas,
        Long maximoDescargas,
        Long totalDescargas
) {
    @Override
    public String toString() {
        return "Idioma: %s | Cantidad: %d | Descargas(Promedio: %.2f | Mín: %d | Máx: %d | Total: %d)".formatted(
                idioma(),
                cantidad(),
                promedioDescargas(),
                minimoDescargas(),
                maximoDescargas(),
                totalDescargas()
        );
    }
}
