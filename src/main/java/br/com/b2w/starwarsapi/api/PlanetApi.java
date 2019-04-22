package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.converter.PlanetInConverter;
import br.com.b2w.starwarsapi.converter.PlanetOutConverter;
import br.com.b2w.starwarsapi.dto.PlanetIn;
import br.com.b2w.starwarsapi.dto.PlanetOut;
import br.com.b2w.starwarsapi.exception.NotFoundException;
import br.com.b2w.starwarsapi.model.Planet;
import br.com.b2w.starwarsapi.service.PlanetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/planets")
public class PlanetApi extends Api {

    private final PlanetService service;
    private final PlanetInConverter inConverter;
    private final PlanetOutConverter outConverter;

    @GetMapping(path = "/search", params = "name")
    public ResponseEntity<PlanetOut> findByName(@RequestParam(value="name") String name) {

        Planet planet = service.findByName(name).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(outConverter.apply(planet));

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PlanetOut> findById(@PathVariable UUID uuid) {

        Planet planet = service.findByUuid(uuid).orElseThrow(NotFoundException::new);
        return ResponseEntity.ok(outConverter.apply(planet));

    }

    @GetMapping
    public ResponseEntity<List<PlanetOut>> findAll(@PageableDefault Pageable pageable) {

        List<PlanetOut> planets = service.findAll(pageable).stream().map(outConverter).collect(Collectors.toList());
        return ResponseEntity.ok(planets);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanetOut> save(@RequestBody @Valid PlanetIn planetIn) {

        Planet planet = service.save(inConverter.apply(planetIn));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(getResourceLocation(planet, "/{uuid}"))
                .body(outConverter.apply(planet));

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {

        service.delete(service.findByUuid(uuid).orElseThrow(NotFoundException::new));
        return noContent();

    }

}
