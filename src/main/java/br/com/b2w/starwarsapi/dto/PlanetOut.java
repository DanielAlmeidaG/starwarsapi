package br.com.b2w.starwarsapi.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlanetOut {

    private UUID uuid;
    private String name;
    private String climate;
    private String terrain;
    private int numberAppearancesFilms;
}
