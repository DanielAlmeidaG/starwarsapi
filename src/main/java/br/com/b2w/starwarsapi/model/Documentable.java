package br.com.b2w.starwarsapi.model;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Persistable;

import java.util.Objects;

public interface Documentable extends Persistable<ObjectId> {

    void setId(ObjectId id);

    @Override
    default boolean isNew() {
        return Objects.nonNull(getId());
    }
}
