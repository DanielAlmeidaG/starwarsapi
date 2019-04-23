package br.com.b2w.starwarsapi.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URI;
import java.util.UUID;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "planets")
public class Planet implements Codded {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private UUID uuid;
    @Indexed(unique = true)
    private String name;
    private String climate;
    private String terrain;
    @Transient
    private int numberAppearancesFilms;
    private URI uri;

}
