package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.IntegrationException;
import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SwapiServiceTest {

    @Autowired
    private SwapiService service;

    private String planetName;
    private String planetWrongName;
    private URI planetURI;
    private URI planetWrongURI;

    @Before
    public void setUp() throws Exception {
        planetName = "Alderaan";
        planetWrongName = "Terra";
        planetURI = URI.create("https://swapi.co/api/planets/2/");
        planetWrongURI = URI.create("https://swapi.co/api/planets/7689/");
    }

    @Test
    public void shouldSearchSwapiPlanetByName() {
        Assert.assertNotNull(service.getSwapiPlanet(planetName));
    }

    @Test
    public void shouldFindSwapiPlanetByUrl() {
        Assert.assertNotNull(service.getSwapiPlanetByUri(planetURI));
    }

    @Test(expected = PlanetNotFoundException.class)
    public void shouldThrowPlanetNotFoundExceptionByName() {
        service.getSwapiPlanet(planetWrongName);
    }

    @Test(expected = IntegrationException.class)
    public void shouldThrowIntegrationException() {
        service.getSwapiPlanetByUri(planetWrongURI);
    }

}
