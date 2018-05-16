package eu.mdabrowski.battles.restapi.battle;

import java.util.Set;

import javax.validation.constraints.NotNull;

import eu.mdabrowski.battles.domain.BattleStatus;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.Timetable;
import eu.mdabrowski.battles.domain.Topic;
import eu.mdabrowski.battles.domain.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BattleDTO {
    private Long id;

    private Set<Long> teams;

    private Set<Long> projects;

    private Long topic;

    private Set<Long> votes;

    private Set<Long> comments;

    private Set<Long> tags;

    BattleStatus battleStatus;

    private Long timetable;
}
