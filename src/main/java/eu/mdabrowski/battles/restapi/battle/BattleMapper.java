package eu.mdabrowski.battles.restapi.battle;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.TimetableRepository;
import eu.mdabrowski.battles.persistance.TopicRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;
import eu.mdabrowski.battles.restapi.mapper.AbstractMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BattleMapper extends AbstractMapper<Battle, BattleDTO> {

    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final TeamRepository teamRepository;
    private final VoteRepository voteRepository;
    private final TopicRepository topicRepository;
    private final TimetableRepository timetableRepository;
    private final ProjectRepository projectRepository;

    public BattleDTO toDTO(Battle battle) {
        return BattleDTO.builder()
                .id(battle.getId())
                .battleStatus(battle.getBattleStatus())
                .comments(setToIds(battle.getComments()))
                .tags(setToIds(battle.getTags()))
                .votes(setToIds(battle.getVotes()))
                .teams(setToIds(battle.getTeams()))
                .projects(setToIds(battle.getProjects()))
                .timetable(Optional.ofNullable(battle.getTimetable()).map(BaseEntity::getId).orElse(null))
                .topic(Optional.ofNullable(battle.getTopic()).map(BaseEntity::getId).orElse(null))
                .build();
    }

    public Battle fromDTO(BattleDTO battleDTO) {
        return Battle.builder()
                .votes(getVotesFromIds(battleDTO.getVotes(), voteRepository))
                .comments(getCommentsFromIds(battleDTO.getComments(), commentRepository))
                .tags(getTagsFromIds(battleDTO.getTags(), tagRepository))
                .teams(getTeamsFromIds(battleDTO.getTags(), teamRepository))
                .projects(getProjectsFromIds(battleDTO.getProjects(), projectRepository))
                .topic(Optional.ofNullable(battleDTO.getTopic())
                        .map(e-> topicRepository.findById(e).orElseThrow(EntityNotFoundException::new))
                        .orElse(null))
                .battleStatus(battleDTO.getBattleStatus())
                .timetable(Optional.ofNullable(battleDTO.getTimetable()).map(e -> timetableRepository.findById(e)
                        .orElseThrow(EntityNotFoundException::new)).orElse(null))
                .build();
    }

    public Battle update(BattleDTO battleDTO, Battle battle) {
        Battle battleFromDTO = fromDTO(battleDTO);

        battle.setComments(battleFromDTO.getComments());
        battle.setTags(battleFromDTO.getTags());
        battle.setTeams(battleFromDTO.getTeams());
        battle.setVotes(battleFromDTO.getVotes());
        battle.setBattleStatus(battleFromDTO.getBattleStatus());
        battle.setTimetable(battleFromDTO.getTimetable());
        battle.setTopic(battleFromDTO.getTopic());
        battle.setProjects(battleFromDTO.getProjects());

        return battle;
    }
}
