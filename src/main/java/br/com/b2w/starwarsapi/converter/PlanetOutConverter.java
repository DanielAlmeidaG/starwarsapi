package br.com.b2w.starwarsapi.converter;

import br.com.b2w.starwarsapi.dto.PlanetOut;
import br.com.b2w.starwarsapi.model.Planet;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlanetOutConverter implements Function<Planet, PlanetOut> {

    @Override
    public PlanetOut apply(Planet planet) {
        PlanetOut planetOut = new PlanetOut();
        planetOut.setUuid(planet.getUuid());
        planetOut.setName(planet.getName());
        planetOut.setClimate(planet.getClimate());
        planetOut.setTerrain(planet.getTerrain());
        planetOut.setAmountMoviesApeared(planet.getAmountMoviesApeared());
        return planetOut;
    }
}
