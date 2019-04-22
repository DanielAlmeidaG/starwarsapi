package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class PlanetAlreadyInsertedException extends HttpClientErrorException {

    private static final long serialVersionUID = -7280209225723855112L;

    public PlanetAlreadyInsertedException() {
        super(HttpStatus.CONFLICT);
    }

    public PlanetAlreadyInsertedException(String statusText) {
        super(HttpStatus.CONFLICT, statusText);
    }

}
