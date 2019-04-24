package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.IntegrationException;
import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import br.com.b2w.starwarsapi.model.SwapiPlanet;
import br.com.b2w.starwarsapi.model.SwapiSearchResult;
import br.com.b2w.starwarsapi.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = "swapi")
public class SwapiService {

    @Value("${integrations.swapi.baseurl}")
    private URI swapiApi;

    private static final String QUERY_SEARCH = "?search=%s";

    private final RestTemplate restTemplate;
    private final MessageUtil messageUtil;

    @Cacheable
    public SwapiPlanet getSwapiPlanet(String planetName) throws IntegrationException, PlanetNotFoundException {

        try {

            log.info("Searching the planet {} in the swapi", planetName);

            SwapiSearchResult searchResult =
                    restTemplate.getForObject(getSearchURI(planetName), SwapiSearchResult.class);

            if(searchResult == null || searchResult.getCount() == 0)
                throw new PlanetNotFoundException(messageUtil.getMessage("swapi.planet.not.found", planetName));

            SwapiPlanet swapiPlanet = Arrays.stream(searchResult.getResults())
                    .filter(planet -> planetName.equals(planet.getName()))
                    .findFirst()
                    .orElseThrow(PlanetNotFoundException::new);

            log.info("Planet recovered: {}", swapiPlanet);

            return swapiPlanet;

        } catch (RestClientException rce) {
            String errorMsg =
                    messageUtil.getMessage("swapi.integration.exception", rce.getLocalizedMessage(), getSearchURI(planetName));
            log.error(errorMsg);
            throw new IntegrationException(errorMsg);
        }

    }

    @Cacheable
    public SwapiPlanet getSwapiPlanetByUri(URI uri) throws IntegrationException, PlanetNotFoundException {

        try {

            log.info("Searching the planet {} in the swapi", uri);

            SwapiPlanet swapiPlanet = restTemplate.getForObject(uri, SwapiPlanet.class);

            log.info("Planet recovered: {}", swapiPlanet);

            return swapiPlanet;

        } catch (RestClientException rce) {
            String errorMsg =
                    messageUtil.getMessage("swapi.integration.exception", rce.getLocalizedMessage(), uri.toString());
            log.error(errorMsg);
            throw new IntegrationException(errorMsg);
        }

    }

    private URI getSearchURI(String planetName) {
        return URI.create(swapiApi + String.format(QUERY_SEARCH, planetName));
    }
}
