package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.IntegrationException;
import br.com.b2w.starwarsapi.exception.PlanetAlreadyInsertedException;
import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import br.com.b2w.starwarsapi.model.Planet;
import br.com.b2w.starwarsapi.model.SwapiPlanet;
import br.com.b2w.starwarsapi.repository.PlanetRepository;
import br.com.b2w.starwarsapi.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class PlanetService {

    private final PlanetRepository repository;

    private final SwapiService swapiService;

    private final MessageUtil messageUtil;

    public Planet save(Planet planet) throws PlanetAlreadyInsertedException {

        log.info("Trying to register the planet {}", planet.getName());

        Optional<Planet> optionalPlanet = repository.findByNameIgnoreCase(planet.getName());

        if(optionalPlanet.isPresent())
            throw new PlanetAlreadyInsertedException(messageUtil.getMessage("planet.name.already.registered", planet.getName()));

        SwapiPlanet swapiPlanet = swapiService.getSwapiPlanet(planet.getName());
        planet.setUri(swapiPlanet.getUrl());

        Planet savedPlanet = repository.save(planet);

        savedPlanet.setNumberAppearancesFilms(swapiPlanet.getNumberAppearancesFilms());

        log.info("Planet {} registered successfully", savedPlanet);

        return savedPlanet;
    }

    public void delete(UUID uuid) throws PlanetNotFoundException {

        log.info("Trying to delete the planet which ID is {}", uuid);

        Optional<Planet> optionalPlanet = repository.findByUuid(uuid);

        if(!optionalPlanet.isPresent())
            throw new PlanetNotFoundException(messageUtil.getMessage("planet.id.not.registered", uuid));

        repository.delete(optionalPlanet.get());

        log.info("Planet {} deleted successfully", uuid);
    }

    public Page<Planet> findAll(Pageable pageable) {
        Page<Planet> allPlanets = repository.findAll(pageable);

        allPlanets.stream().forEach(this::getUpdatedPlanet);

        return allPlanets;
    }

    public Planet findByName(String name) throws PlanetNotFoundException {

        log.info("Searching by name the planet {} on the database", name);

        Optional<Planet> optionalPlanet = repository.findByNameIgnoreCase(name);

        if(!optionalPlanet.isPresent())
            throw new PlanetNotFoundException(messageUtil.getMessage("planet.name.not.registered", name));

        Planet planet = getUpdatedPlanet(optionalPlanet.get());

        log.info("Planet found: {}", planet);

        return planet;
    }

    public Planet findByUuid(UUID uuid) throws PlanetNotFoundException {

        log.info("Searching the planet which ID is {} on the database", uuid);

        Optional<Planet> optionalPlanet = repository.findByUuid(uuid);

        if(!optionalPlanet.isPresent())
            throw new PlanetNotFoundException(messageUtil.getMessage("planet.id.not.registered", uuid));

        Planet planet = getUpdatedPlanet(optionalPlanet.get());

        log.info("Planet found: {}", planet);

        return planet;
    }

    private Planet getUpdatedPlanet(Planet planet) {
        planet.setNumberAppearancesFilms(swapiService.getSwapiPlanetByUri(planet.getUri()).getNumberAppearancesFilms());

        log.info("Number of appearances in movies for planet {} update to {}",
                planet.getName(), planet.getNumberAppearancesFilms());

        return planet;
    }

}
