package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class PlanetAlreadyInsertedException extends HttpClientErrorException {

    private static final long serialVersionUID = -4844548810264075678L;

    public PlanetAlreadyInsertedException() {
        super(HttpStatus.CONFLICT);
    }

    public PlanetAlreadyInsertedException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
