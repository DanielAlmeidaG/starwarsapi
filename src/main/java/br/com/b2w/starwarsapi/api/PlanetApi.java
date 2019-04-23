package br.com.b2w.starwarsapi.api;

import br.com.b2w.starwarsapi.converter.PlanetInConverter;
import br.com.b2w.starwarsapi.converter.PlanetOutConverter;
import br.com.b2w.starwarsapi.dto.PlanetIn;
import br.com.b2w.starwarsapi.dto.PlanetOut;
import br.com.b2w.starwarsapi.service.PlanetService;
import br.com.b2w.starwarsapi.util.MessageUtil;
import com.weddini.throttling.Throttling;
import com.weddini.throttling.ThrottlingType;
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
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unchecked")
@AllArgsConstructor
@RestController
@RequestMapping("/planets")
public class PlanetApi extends Api {

    private final PlanetService service;
    private final PlanetInConverter inConverter;
    private final PlanetOutConverter outConverter;
    private final MessageUtil messageUtil;

    @GetMapping(path = "/search", params = "name")
    @Throttling(type = ThrottlingType.RemoteAddr, timeUnit = TimeUnit.MINUTES, limit = 100)
    public ResponseEntity<PlanetOut> findByName(@RequestParam(value="name") String name) {

        if(name==null || name.isEmpty())
            throw new BadRequestException(messageUtil.getMessage("planet.name.required"));

        return ok(service.findByName(name), outConverter);

    }

    @GetMapping("/{uuid}")
    @Throttling(type = ThrottlingType.RemoteAddr, timeUnit = TimeUnit.MINUTES, limit = 100)
    public ResponseEntity<PlanetOut> findById(@PathVariable UUID uuid) {

        return ok(service.findByUuid(uuid), outConverter);

    }

    @GetMapping
    @Throttling(type = ThrottlingType.RemoteAddr, timeUnit = TimeUnit.HOURS, limit = 1000)
    public ResponseEntity<List<PlanetOut>> findAll(@PageableDefault Pageable pageable) {

        return ok(service.findAll(pageable), outConverter);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Throttling(type = ThrottlingType.RemoteAddr, timeUnit = TimeUnit.DAYS, limit = 1000)
    public ResponseEntity<PlanetOut> save(@RequestBody @Valid PlanetIn planetIn) {

        return created(service.save(inConverter.apply(planetIn)), outConverter);

    }

    @DeleteMapping("/{uuid}")
    @Throttling(type = ThrottlingType.RemoteAddr, timeUnit = TimeUnit.DAYS, limit = 1000)
    public ResponseEntity<?> delete(@PathVariable UUID uuid) {

        service.delete(uuid);
        return noContent();

    }

}
