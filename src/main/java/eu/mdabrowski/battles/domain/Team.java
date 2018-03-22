package eu.mdabrowski.battles.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "baseEntities")
public class Team extends BaseEntity {

    @NotBlank
    private String name;

    private Set<User> users;
}
