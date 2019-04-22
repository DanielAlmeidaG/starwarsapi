package br.com.b2w.starwarsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiSearchResult {

    private int count;
    private SwapiPlanet[] results;
}
