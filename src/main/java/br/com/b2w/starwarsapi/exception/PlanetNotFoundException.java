package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class PlanetNotFoundException extends HttpClientErrorException {

    private static final long serialVersionUID = -7280209225723855022L;

    public PlanetNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public PlanetNotFoundException(String statusText) {
        super(HttpStatus.NOT_FOUND, statusText);
    }
}
