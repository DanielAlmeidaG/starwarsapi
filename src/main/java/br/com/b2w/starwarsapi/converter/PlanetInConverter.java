package br.com.b2w.starwarsapi.converter;

import br.com.b2w.starwarsapi.dto.PlanetIn;
import br.com.b2w.starwarsapi.model.Planet;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Function;

@Component
public class PlanetInConverter implements Function<PlanetIn, Planet> {

    @Override
    public Planet apply(PlanetIn planetIn) {
        Planet planet = new Planet();
        planet.setUuid(UUID.randomUUID());
        planet.setName(planetIn.getName());
        planet.setClimate(planetIn.getClimate());
        planet.setTerrain(planetIn.getTerrain());
        return planet;
    }
}
