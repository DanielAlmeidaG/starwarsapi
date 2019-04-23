package br.com.b2w.starwarsapi.exception;

public class IntegrationException extends RuntimeException {

    private static final long serialVersionUID = -7280209225723855022L;

    public IntegrationException() {
    }

    public IntegrationException(String message) {
        super(message);
    }
}
