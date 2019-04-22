package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.converter.PlanetInConverter;
import br.com.b2w.starwarsapi.converter.PlanetOutConverter;
import br.com.b2w.starwarsapi.dto.PlanetIn;
import br.com.b2w.starwarsapi.dto.PlanetOut;
import br.com.b2w.starwarsapi.exception.PlanetNotFoundException;
import br.com.b2w.starwarsapi.model.Planet;
import br.com.b2w.starwarsapi.service.PlanetService;
import br.com.b2w.starwarsapi.util.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@RestController
@AllArgsConstructor
@RequestMapping("/planets")
public class PlanetApi extends Api {

    private final PlanetService service;
    private final PlanetInConverter inConverter;
    private final PlanetOutConverter outConverter;
    private final MessageUtil messageUtil;

    @GetMapping(path = "/search", params = "name")
    public ResponseEntity<PlanetOut> findByName(@RequestParam(value="name") String name) {

        if(name==null || name.isEmpty())
            throw new BadRequestException(messageUtil.getMessage("planet.name.required"));

        Planet planet = service.findByName(name).orElseThrow(PlanetNotFoundException::new);
        return ok(planet, outConverter);

    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PlanetOut> findById(@PathVariable UUID uuid) {

        Planet planet = service.findByUuid(uuid).orElseThrow(PlanetNotFoundException::new);
        return ok(planet, outConverter);

    }

    @GetMapping
    public ResponseEntity<List<PlanetOut>> findAll(@PageableDefault Pageable pageable) {

        List<PlanetOut> planets = service.findAll(pageable).stream().map(outConverter).collect(Collectors.toList());
        return ok(planets);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlanetOut> save(@RequestBody @Valid PlanetIn planetIn) {

        Planet planet = service.save(inConverter.apply(planetIn));
        return created(planet, outConverter);

    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {

        service.delete(service.findByUuid(uuid).orElseThrow(PlanetNotFoundException::new));
        return noContent();

    }

}
