package br.com.b2w.starwarsapi.exception;

public class PlanetAlreadyInsertedException extends RuntimeException {

    private static final long serialVersionUID = -7280209225723855112L;

    public PlanetAlreadyInsertedException() {
    }

    public PlanetAlreadyInsertedException(String message) {
        super(message);
    }
}
