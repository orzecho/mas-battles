package eu.mdabrowski.battles.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

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
@Builder
@Entity
public class Topic extends BaseEntity implements Votable, Commentable{
    private String value;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes;

    @OneToMany
    private Set<Comment> comments;

    @OneToMany
    private Set<Battle> battles;
}
