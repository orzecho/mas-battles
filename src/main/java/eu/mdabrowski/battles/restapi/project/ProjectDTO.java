package eu.mdabrowski.battles.restapi.project;

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
public class ProjectDTO {
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Long team;

    private Long battle;

    private Set<Long> votes;

    private Set<Long> comments;

    private Set<Long> tags;
}
