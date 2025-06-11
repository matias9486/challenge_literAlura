package com.alura.literalura.service;

import com.alura.literalura.model.dto.LibroDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class GutendexService {
    private String urlBase = "https://gutendex.com/books/";
    private String urlBuscar = "?search=";
    private HttpClient client;
    public GutendexService() {
        this.client = HttpClient.newHttpClient();
    }

    public List<LibroDTO> buscarLibroAutor(String titulo) throws IOException, InterruptedException, IllegalArgumentException {
        if(titulo.isBlank())
            throw new IllegalArgumentException("Ingrese un título válido.");

        URI direccion = URI.create(urlBase + urlBuscar + titulo.replace(" ", "%20"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String jsonResponse = response.body();

        ObjectMapper mapper = new ObjectMapper();

        // Parsea el JSON a un árbol.
        JsonNode rootNode = mapper.readTree(jsonResponse);
        // Obtener el nodo "results"
        JsonNode resultsNode = rootNode.get("results");
        // Convertir resultado a lista de objetos Java
        List<LibroDTO> listaResultados = mapper.readerForListOf(LibroDTO.class).readValue(resultsNode);

        return listaResultados.stream().
                    filter(l -> l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                    .collect(Collectors.toList());
    }
}
