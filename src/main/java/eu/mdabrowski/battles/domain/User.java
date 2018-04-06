package eu.mdabrowski.battles.domain;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User extends BaseEntity {
    @NotBlank
    private String login;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }
}
