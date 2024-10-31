package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataSerie(
    @JsonAlias("Title") String titulo,
    @JsonAlias("totalSeasons") int totaldeTemporadas,
    @JsonAlias("imdbRating") String evaluacion

    /* 
     * JsonAlias= Solamente leer
     * JsonProperty= Permite leer y escribir;
     */
) {
    
}
