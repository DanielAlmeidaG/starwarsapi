package br.com.b2w.starwarsapi.exception;

public class PlanetNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -7280209225723855022L;

    public PlanetNotFoundException() {
    }

    public PlanetNotFoundException(String message) {
        super(message);
    }
}
