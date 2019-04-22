package br.com.b2w.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiPlanet {

    private String name;
    private URI[] films;
    private URI url;

    public int getAmountMoviesApeared() {
        return this.getFilms() == null ? 0 : this.getFilms().length;
    }
}
