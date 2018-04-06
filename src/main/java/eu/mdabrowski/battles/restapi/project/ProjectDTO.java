package eu.mdabrowski.battles.restapi.project;

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
public class ProjectDTO {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long team;

    private Set<Long> votes;

    private Set<Long> comments;

    private Set<Long> tags;
}
