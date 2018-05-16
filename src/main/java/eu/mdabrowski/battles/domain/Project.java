package eu.mdabrowski.battles.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import eu.mdabrowski.battles.associations.Association;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Project extends BaseEntity implements Votable, Commentable, Taggable {
    public static final String LABEL_SINGULAR = "project";
    public static final String LABEL_PLURAL = "projects";

    @NotBlank
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name="team_id", nullable = false)
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Association(name = "has votes", ourMultiplicity = "1", theirMultiplicity = "0..2")
    private Set<Vote> votes;

    @OneToMany
    private Set<Comment> comments;

    @ManyToMany
    private Set<Tag> tags;

    @OneToOne(mappedBy = "project")
    private SongProject songProject;

    public void setSongProject(SongProject songProject){
        if(songProject != null) {
            this.songProject = songProject;
            this.songProject.setProject(this);
        } else {
            if(this.songProject != null) {
                this.songProject.setProject(null);
            }
            this.songProject = null;
        }
    }

    @OneToOne(mappedBy = "project")
    private DubbingProject dubbingProject;

    public void setDubbingProject(DubbingProject dubbingProject){
        if(dubbingProject != null) {
            this.dubbingProject = dubbingProject;
            this.dubbingProject.setProject(this);
        } else {
            if(this.dubbingProject != null) {
                this.dubbingProject.setProject(null);
            }
            this.dubbingProject = null;
        }
    }

    @OneToOne(mappedBy = "project")
    private OtherProject otherProject;

    public void setOtherProject(OtherProject otherProject){
        if(otherProject != null) {
            this.otherProject = otherProject;
            this.otherProject.setProject(this);
        } else {
            if(this.otherProject != null) {
                this.otherProject.setProject(null);
            }
            this.otherProject = null;
        }
    }

    //MAS kwalifikowana
    public Vote getVoteByUser(User user) {
        return Optional.ofNullable(votes)
                .map(e -> e.stream()
                        .filter(f -> f.getUser().equals(user))
                        .findFirst()
                        .orElse(null))
                .orElse(null);
    }

    public void addVote(@Valid Vote vote) {
        Optional.ofNullable(getVotes()).orElseGet(HashSet::new).add(vote);
        vote.setProject(this);
    }
}
