package eu.mdabrowski.battles.restapi.project;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import static eu.mdabrowski.battles.restapi.MapperUtil.setToIds;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectMapper {

    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final TeamRepository teamRepository;

    public ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .team(project.getTeam().getId())
                .comments(setToIds(project.getComments()))
                .tags(setToIds(project.getTags()))
                .votes(setToIds(project.getVotes()))
                .build();
    }

    public Project fromDTO(ProjectDTO projectDTO) {
        return Project.builder()
                .name(projectDTO.getName())
                .team(teamRepository.findById(projectDTO.getTeam()).orElseThrow(EntityNotFoundException::new))
                .comments(getCommentsFromIds(projectDTO.getComments(), commentRepository))
                .tags(getTagsFromIds(projectDTO.getTags(), tagRepository))
                .team(Optional.ofNullable(projectDTO.getTeam()).map(teamId -> teamRepository.findById(teamId)
                        .orElseThrow(EntityNotFoundException::new)).orElse(null))
                .build();
    }

    private Set<Comment> getCommentsFromIds(Set<Long> ids, JpaRepository<Comment, Long> repository) {
        if(ids == null || ids.isEmpty()){
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    private Set<Tag> getTagsFromIds(Set<Long> ids, JpaRepository<Tag, Long> repository) {
        if(ids == null || ids.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    public Project update(ProjectDTO projectDTO, Project project) {
        Project projectFromDTO = fromDTO(projectDTO);

        project.setName(projectFromDTO.getName());
        project.setComments(projectFromDTO.getComments());
        project.setTags(projectFromDTO.getTags());
        project.setTeam(projectFromDTO.getTeam());
        project.setVotes(projectFromDTO.getVotes());

        return project;
    }
}
