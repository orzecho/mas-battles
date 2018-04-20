package eu.mdabrowski.battles.domain;

import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    @OneToMany
    private Set<UserRole> userRoles;

    public Optional<Team> getTeam() {
        return Optional.ofNullable(team);
    }
}
