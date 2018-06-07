package eu.mdabrowski.battles.restapi.team;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {
    private Long id;

    @NotNull
    private String name;

    private Long leader;

    Set<Long> users;
}
