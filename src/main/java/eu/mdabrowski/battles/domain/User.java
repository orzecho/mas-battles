package eu.mdabrowski.battles.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {
    @NotBlank
    private String login;

    private Team team;

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }
}
