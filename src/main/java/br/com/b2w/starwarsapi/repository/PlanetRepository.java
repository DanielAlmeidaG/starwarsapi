package br.com.b2w.starwarsapi.repository;

import br.com.b2w.starwarsapi.model.Planet;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<Planet, ObjectId> {

    Optional<Planet> findByNameIgnoreCase(String name);
    Optional<Planet> findByUuid(UUID uuid);

}
