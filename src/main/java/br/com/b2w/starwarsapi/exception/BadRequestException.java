package br.com.b2w.starwarsapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class BadRequestException extends HttpClientErrorException {

    private static final long serialVersionUID = 2091565688254696186L;

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String statusText) {
        super(HttpStatus.BAD_REQUEST, statusText);
    }

}
