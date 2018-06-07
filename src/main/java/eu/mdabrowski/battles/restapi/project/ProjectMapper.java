package eu.mdabrowski.battles.restapi.project;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.persistance.BattleRepository;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;
import eu.mdabrowski.battles.restapi.mapper.AbstractMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectMapper extends AbstractMapper<Project, ProjectDTO> {

    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final TeamRepository teamRepository;
    private final VoteRepository voteRepository;
    private final BattleRepository battleRepository;

    public ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .team(project.getTeam().getId())
                .battle(Optional.ofNullable(project.getBattle()).map(BaseEntity::getId).orElse(null))
                .comments(setToIds(project.getComments()))
                .tags(setToIds(project.getTags()))
                .votes(setToIds(project.getVotes()))
                .description(project.getDescription())
                .build();
    }

    public Project fromDTO(ProjectDTO projectDTO) {
        return Project.builder()
                .name(projectDTO.getName())
                .comments(getCommentsFromIds(projectDTO.getComments(), commentRepository))
                .tags(getTagsFromIds(projectDTO.getTags(), tagRepository))
                .votes(getVotesFromIds(projectDTO.getVotes(), voteRepository))
                .team(Optional.ofNullable(projectDTO.getTeam()).map(teamId -> teamRepository.findById(teamId)
                        .orElseThrow(EntityNotFoundException::new)).orElse(null))
                .description(projectDTO.getDescription())
                .battle(getBattleFromId(projectDTO.getBattle()))
                .build();
    }

    private Battle getBattleFromId(Long battle) {
        return battleRepository.getOne(battle);
    }

    public Project update(ProjectDTO projectDTO, Project project) {
        Project projectFromDTO = fromDTO(projectDTO);

        project.setName(projectFromDTO.getName());
        project.setComments(projectFromDTO.getComments());
        project.setTags(projectFromDTO.getTags());
        project.setTeam(projectFromDTO.getTeam());
        project.setVotes(projectFromDTO.getVotes());
        project.setDescription(projectFromDTO.getDescription());
        project.setBattle(projectFromDTO.getBattle());

        return project;
    }
}
