package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.model.Codded;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public abstract class Api {

    protected ResponseEntity noContent() {
        return ResponseEntity.noContent().build();
    }

    protected <T extends Codded> URI getResourceLocation(@NonNull T resource,
                                                         @NonNull String path) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(resource.getUuid()).toUri();
    }
}
