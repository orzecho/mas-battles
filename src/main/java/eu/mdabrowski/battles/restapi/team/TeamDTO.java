package eu.mdabrowski.battles.restapi.team;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;

    @NotNull
    private String name;

    Set<Long> users;
}
