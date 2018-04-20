package eu.mdabrowski.battles.domain;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(uniqueConstraints=
                { @UniqueConstraint(columnNames={"battle_id", "id"})
                , @UniqueConstraint(columnNames={"topic_id", "id"})
                , @UniqueConstraint(columnNames={"project_id", "id"})}
)
public class Vote extends BaseEntity {
//    @PostConstruct
//    @SneakyThrows
//    public void validate(){
//        if(!isVotableNotNull()) throw new Exception();
//        if(!isOnlyOneVotable()) throw new Exception();
//    }

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @AssertTrue
    @Transient
    public boolean isVotableNotNull() {
        return battle != null || topic != null || project != null;
    }

    @AssertTrue
    @Transient
    public boolean isOnlyOneVotable() {
        return     !(battle != null && topic   != null)
                && !(battle != null && project != null)
                && !(topic  != null && project != null);
    }

    public void setProject(Project project) {
        this.project = project;
        project.addVote(this);
    }
}
