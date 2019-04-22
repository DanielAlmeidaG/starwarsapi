package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.model.Codded;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

public abstract class Api {

    private static final String UUID = "/{uuid}";

    protected ResponseEntity noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T extends Object> ResponseEntity ok(T resource) {
        return ResponseEntity.ok(resource);
    }

    protected <T extends Object> ResponseEntity ok(T resource, Function converter) {
        return ResponseEntity.ok(converter.apply(resource));
    }

    protected <T extends Codded> ResponseEntity created(T resource, Function converter) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(getResourceLocation(resource))
                .body(converter.apply(resource));
    }

    protected <T extends Codded> URI getResourceLocation(@NonNull T resource) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(UUID)
                .buildAndExpand(resource.getUuid()).toUri();
    }
}
