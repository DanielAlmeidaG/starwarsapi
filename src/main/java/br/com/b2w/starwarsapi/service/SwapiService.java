package br.com.b2w.starwarsapi.service;

import br.com.b2w.starwarsapi.exception.NotFoundException;
import br.com.b2w.starwarsapi.model.SwapiPlanet;
import br.com.b2w.starwarsapi.model.SwapiSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SwapiService {

    @Value("${integrations.swapi.baseurl}")
    private URI swapiApi;

    @Value("${integrations.http.headers.useragent}")
    private String userAgent;

    private static final String QUERY_SEARCH = "?search=%s";
    private static final String USER_AGENT = "user-agent";
    private static final String PARAMETERS = "parameters";

    private final RestTemplate restTemplate;

    @Cacheable("SwapiPlanetsSearch")
    public SwapiPlanet getSwapiPlanet(String planetName) throws RestClientException {

        URI searchURI = URI.create(swapiApi + String.format(QUERY_SEARCH, planetName));

        ResponseEntity<SwapiSearchResult> response =
                restTemplate.exchange(searchURI, HttpMethod.GET, getHttpEntity(), SwapiSearchResult.class);

        SwapiSearchResult searchResult = response.getBody();

        if(searchResult == null || searchResult.getCount() == 0)
            throw new NotFoundException("Planeta " + planetName + " não encontrado na swapi.");

        return Arrays.stream(searchResult.getResults())
                .filter(swapiPlanet -> planetName.equals(swapiPlanet.getName()))
                .findAny()
                .orElseThrow(NotFoundException::new);
    }

    @Cacheable("SwapiPlanetUri")
    public SwapiPlanet getSwapiPlanetByUri(URI uri) throws RestClientException {

        ResponseEntity<SwapiPlanet> response =
                restTemplate.exchange(uri, HttpMethod.GET, getHttpEntity(), SwapiPlanet.class);

        SwapiPlanet swapiPlanet = response.getBody();

        if(swapiPlanet == null)
            throw new NotFoundException("");

        return swapiPlanet;
    }

    private HttpEntity<String> getHttpEntity() {
        return new HttpEntity<>(PARAMETERS, getHttpHeaders());
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(USER_AGENT, userAgent);
        return headers;
    }
}
