package br.com.b2w.starwarsapi.repository;

import br.com.b2w.starwarsapi.model.Planet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataMongoTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository repository;

    private Planet planet;
    private Planet existingPlanet;

    @Before
    public void setUp() throws Exception {

        planet = new Planet();
        planet.setName("Kamino");
        planet.setUuid(UUID.randomUUID());
        planet.setClimate("Good");
        planet.setTerrain("Fair");
        planet.setUri(URI.create("https://swapi.co/api/planets/10/"));

        existingPlanet = new Planet();
        existingPlanet.setName("Alderaan");
        existingPlanet.setClimate("Good");
        existingPlanet.setTerrain("Fair");
        existingPlanet.setUri(URI.create("https://swapi.co/api/planets/2/"));

    }

    @Test
    public void shouldAddANewPlanet() {
        Planet newPlanet = repository.save(planet);
        Assert.assertNotNull(newPlanet);
    }

    @Test
    public void shouldFindAPlanetByName() {
        Optional<Planet> aPlanet = repository.findByNameIgnoreCase(existingPlanet.getName());
        Assert.assertTrue(aPlanet.isPresent());
    }

    @Test
    public void shouldFindAPlanetByUuid() {

        UUID uuid = repository.findByNameIgnoreCase(existingPlanet.getName()).get().getUuid();

        Optional<Planet> aPlanet = repository.findByUuid(uuid);
        Assert.assertTrue(aPlanet.isPresent());
    }

    @Test
    public void shouldFindAllPlanets() {
        Page<Planet> allPlanets = repository.findAll(Pageable.unpaged());
        Assert.assertThat(allPlanets.getTotalPages(), is(greaterThan(0)));
    }

    @Test
    public void shouldRemoveAPlanet() {
        repository.findByNameIgnoreCase(existingPlanet.getName()).ifPresent(planet1 -> repository.delete(planet1));
        Assert.assertFalse(repository.findByUuid(existingPlanet.getUuid()).isPresent());
    }

    @Test
    public void shouldRestartResources() {
        Optional<Planet> savedPlanet = repository.findByNameIgnoreCase(planet.getName());
        savedPlanet.ifPresent(planet -> repository.delete(planet));

        existingPlanet.setUuid(UUID.randomUUID());
        repository.save(existingPlanet);

        Assert.assertNotNull(existingPlanet.getName());
    }

}
