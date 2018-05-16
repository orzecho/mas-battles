package eu.mdabrowski.battles.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Battle extends BaseEntity implements Votable, Commentable, Taggable, TimedEvent {
    public static final String LABEL_PLURAL = "battles";
    public static final String LABEL_SINGULAR = "battle";
    @OneToMany
    private Set<Team> teams;

    @OneToMany
    private Set<Project> projects;

    @ManyToOne
    @JoinColumn(name="topic_id", nullable = false)
    private Topic topic;

    @OneToMany
    private Set<Vote> votes;

    @OneToMany
    private Set<Comment> comments;

    @ManyToMany
    private Set<Tag> tags;

    BattleStatus battleStatus;

    @OneToOne
    private Timetable timetable;
}