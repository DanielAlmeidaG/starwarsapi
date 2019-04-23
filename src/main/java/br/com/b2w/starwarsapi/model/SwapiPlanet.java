package br.com.b2w.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.net.URI;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiPlanet {

    private String name;
    private URI[] films;
    private URI url;

    public int getNumberAppearancesFilms() {
        return this.getFilms() == null ? 0 : this.getFilms().length;
    }
}
