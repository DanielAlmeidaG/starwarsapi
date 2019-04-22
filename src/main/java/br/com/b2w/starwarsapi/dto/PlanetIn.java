package br.com.b2w.starwarsapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PlanetIn {

    @NotNull(message = "{planet.in.name.not.null}")
    @NotEmpty(message = "{planet.in.name.not.empty}")
    private String name;

    @NotNull(message = "{planet.in.climate.not.null}")
    @NotEmpty(message = "{planet.in.climate.not.empty}")
    private String climate;

    @NotNull(message = "{planet.in.terrain.not.null}")
    @NotEmpty(message = "{planet.in.terrain.not.empty}")
    private String terrain;

}
