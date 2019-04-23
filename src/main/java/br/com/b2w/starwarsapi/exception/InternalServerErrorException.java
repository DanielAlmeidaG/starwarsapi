package br.com.b2w.starwarsapi.exception;

public class InternalServerErrorException extends RuntimeException {

    private static final long serialVersionUID = -7280209225723855002L;

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
