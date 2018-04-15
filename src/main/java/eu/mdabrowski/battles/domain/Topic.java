package eu.mdabrowski.battles.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Topic extends BaseEntity implements Votable, Commentable{
    private String value;

    @OneToMany
    private Set<Vote> votes;

    @OneToMany
    private Set<Comment> comments;

    @OneToMany
    private Set<Battle> battles;
}
