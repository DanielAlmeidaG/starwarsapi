package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.model.Planet;
import br.com.b2w.starwarsapi.service.PlanetService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanetApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanetService service;

    @Test
    public void shouldFindPlanetByName() throws Exception {
        String planetName = getRegisteredPlanet().getName();
        mockMvc.perform(get("/planets/search?name=" + planetName).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", planetName).exists());
    }

    @Test
    public void shouldFindPlanetByUuid() throws Exception {
        UUID uuid = getRegisteredPlanet().getUuid();
        mockMvc.perform(get("/planets/" + uuid.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.uuid", uuid).exists());
    }

    @Test
    public void shouldFindAllPlanets() throws Exception {
        this.mockMvc.perform(get("/planets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]").exists());
    }

    @Test
    public void shouldSaveAPlanet() throws Exception {
        Planet newPlanet = getNewPlanet();
        String jsonPlanet = getJsonPlanet(newPlanet);
        this.mockMvc.perform(post("/planets").contentType(MediaType.APPLICATION_JSON).content(jsonPlanet))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", newPlanet.getName()).exists())
                .andExpect(jsonPath("$.climate", newPlanet.getClimate()).exists())
                .andExpect(jsonPath("$.terrain", newPlanet.getTerrain()).exists())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.numberAppearancesFilms").exists());

        service.delete(service.findByName(newPlanet.getName()).getUuid());

    }

    @Test
    public void shouldDeleteAPlanet() throws Exception {
        UUID uuid = service.save(getPlanetToBeDeleted()).getUuid();

        mockMvc.perform(delete("/planets/" + uuid).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    //TODO create more Tests for Exception Cases and Rate Limiting

    private String getJsonPlanet(Planet newPlanet) {
        return String.format("{\"name\": \"%s\",\"climate\": \"%s\",\"terrain\": \"%s\"}",
                newPlanet.getName(), newPlanet.getClimate(), newPlanet.getTerrain());
    }

    private Planet getRegisteredPlanet() {
        return service.findByName("Alderaan");
    }

    private Planet getNewPlanet() {
        Planet planet = new Planet();
        planet.setName("Hoth");
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
}
