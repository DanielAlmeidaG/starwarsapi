package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import br.com.b2w.starwarsapi.model.SwapiPlanet;
import br.com.b2w.starwarsapi.model.SwapiSearchResult;
import br.com.b2w.starwarsapi.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SwapiService {

    @Value("${integrations.swapi.baseurl}")
    private URI swapiApi;

    @Value("${integrations.http.headers.useragent}")
    private String userAgent;

    private static final String QUERY_SEARCH = "?search=%s";

    private final RestTemplate restTemplate;
    private final MessageUtil messageUtil;

    @Cacheable("SwapiPlanetsSearch")
    public SwapiPlanet getSwapiPlanet(String planetName) throws RestClientException {

        URI searchURI = URI.create(swapiApi + String.format(QUERY_SEARCH, planetName));

        SwapiSearchResult searchResult = restTemplate.getForObject(searchURI, SwapiSearchResult.class);

        if(searchResult == null || searchResult.getCount() == 0)
            throw new PlanetNotFoundException(messageUtil.getMessage("planet.swapi.not.found", planetName));

        return Arrays.stream(searchResult.getResults())
                .filter(swapiPlanet -> planetName.equals(swapiPlanet.getName()))
                .findAny()
                .orElseThrow(PlanetNotFoundException::new);
    }

    @Cacheable("SwapiPlanetUri")
    public SwapiPlanet getSwapiPlanetByUri(URI uri) throws RestClientException {

        SwapiPlanet swapiPlanet = restTemplate.getForObject(uri, SwapiPlanet.class);

        if(swapiPlanet == null)
            throw new PlanetNotFoundException(messageUtil.getMessage("planet.swapi.not.found", uri));

        return swapiPlanet;
    }

}
