package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.PlanetAlreadyInsertedException;
import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import br.com.b2w.starwarsapi.model.Planet;
import br.com.b2w.starwarsapi.repository.PlanetRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetServiceTest {

    @Autowired
    private PlanetService service;

    @Autowired
    private PlanetRepository repository;

    @Test
    public void shouldSaveAValidPlanet() {

        Planet newPlanet = service.save(getNewPlanet());

        Assert.assertNotNull(newPlanet);

        service.delete(newPlanet.getUuid());

    }

    @Test(expected = PlanetAlreadyInsertedException.class)
    public void shouldThrowPlanetAlreadyInsertedException() {
        service.save(getRegisteredPlanet());
    }

    @Test
    public void shouldDeleteAValidPlanet() {

        Planet planet = service.save(getPlanetToBeDeleted());

        service.delete(planet.getUuid());

        Assert.assertFalse(repository.findByUuid(planet.getUuid()).isPresent());
    }

    @Test(expected = PlanetNotFoundException.class)
    public void shouldThrowPlanetNotFoundExceptionInDelete() {
        service.delete(getRandomUuid());
    }

    @Test
    public void shouldFindAllPlanets() {
        int pages = service.findAll(Pageable.unpaged()).getTotalPages();
        Assert.assertTrue(pages > 0);
    }

    @Test
    public void shouldFindAPlanetByName() {
        Assert.assertNotNull(service.findByName(getRegisteredPlanet().getName()));
    }

    @Test(expected = PlanetNotFoundException.class)
    public void shouldThrowPlanetNotFoundExceptionInFindByName() {
        service.findByName(getInvalidPlanet().getName());
    }

    @Test
    public void shouldFindAPlanetByUuid() {
        Planet planet = repository.findByNameIgnoreCase(getRegisteredPlanet().getName()).get();
        Assert.assertNotNull(service.findByUuid(planet.getUuid()));
    }

    @Test(expected = PlanetNotFoundException.class)
    public void shouldThrowPlanetNotFoundExceptionInFindByUuid() {
        service.findByUuid(getRandomUuid());
    }

    private Planet getNewPlanet() {
        Planet planet = new Planet();
        planet.setName("Hoth");
        planet.setUuid(UUID.randomUUID());
        planet.setClimate("frozen");
        planet.setTerrain("tundra");
        return planet;
    }

    private Planet getPlanetToBeDeleted() {
        Planet planet = new Planet();
        planet.setName("Bespin");
        planet.setUuid(UUID.randomUUID());
        planet.setClimate("temperate");
        planet.setTerrain("giant");
        return planet;
    }

    private Planet getRegisteredPlanet() {
        Planet planet = new Planet();
        planet.setName("Alderaan");
        return planet;
    }

    private Planet getInvalidPlanet() {
        Planet planet = new Planet();
        planet.setName("Terra");
        return planet;
    }

    private UUID getRandomUuid() {
        return UUID.randomUUID();
    }
}