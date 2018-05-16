package eu.mdabrowski.battles.restapi.mapper;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.Vote;

public abstract class AbstractMapper<ENTITY, DTO>{
    public abstract DTO toDTO(ENTITY entity);
    public abstract ENTITY fromDTO(DTO dto);
    public abstract ENTITY update(DTO dto, ENTITY entity);

    protected Set<Comment> getCommentsFromIds(Set<Long> ids, JpaRepository<Comment, Long> repository) {
        if(ids == null || ids.isEmpty()){
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    protected Set<Tag> getTagsFromIds(Set<Long> ids, JpaRepository<Tag, Long> repository) {
        if(ids == null || ids.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    protected Set<Vote> getVotesFromIds(Set<Long> ids, JpaRepository<Vote, Long> repository) {
        if(ids == null || ids.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    protected Set<Team> getTeamsFromIds(Set<Long> ids, JpaRepository<Team, Long> repository) {
        if(ids == null || ids.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    protected Set<Project> getProjectsFromIds(Set<Long> ids, JpaRepository<Project, Long> repository) {
        if(ids == null || ids.isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    protected Set<Long> setToIds(Set<? extends BaseEntity> baseEntities) {
        return Optional.ofNullable(baseEntities).orElse(new HashSet<>()).stream()
                .map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
