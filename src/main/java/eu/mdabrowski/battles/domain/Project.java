package eu.mdabrowski.battles.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Project extends BaseEntity implements Votable, Commentable, Taggable {
    @NotBlank
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @OneToMany
    private Set<Vote> votes = new HashSet<>();

    @OneToMany
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
}
