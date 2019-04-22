package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class InternalServerErrorException extends HttpClientErrorException {

    private static final long serialVersionUID = -7280209225723855002L;

    public InternalServerErrorException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String statusText) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, statusText);
    }

}
