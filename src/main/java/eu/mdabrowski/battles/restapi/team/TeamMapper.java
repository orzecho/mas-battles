package eu.mdabrowski.battles.restapi.team;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import static eu.mdabrowski.battles.restapi.MapperUtil.setToIds;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeamMapper {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TeamDTO toDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .users(setToIds(team.getUsers()))
                .leader(Optional.ofNullable(team.getLeader()).map(BaseEntity::getId).orElse(null))
                .build();
    }

    public Team fromDTO(TeamDTO teamDTO) {
        return Team.builder()
                .name(teamDTO.getName())
//                .users(getUsersFromIds(teamDTO.getUsers(), userRepository))
                .leader(Optional.ofNullable(teamDTO.getLeader()).map(leader -> userRepository.findById(leader)
                        .orElseThrow(EntityNotFoundException::new))
                        .orElse(null))
                .build();
    }

    private Set<User> getUsersFromIds(Set<Long> ids, JpaRepository<User, Long> repository) {
        if(ids == null || ids.isEmpty()){
            return new HashSet<>();
        } else {
            return new HashSet<>(repository.findAllById(ids));
        }
    }

    public Team update(TeamDTO teamDTO, Team team) {
        Team teamFromDTO = fromDTO(teamDTO);

        team.setName(teamFromDTO.getName());
//        team.setUsers(teamFromDTO.getUsers());
        team.setLeader(teamFromDTO.getLeader());

        return team;
    }

    //MAS
    public Team update(TeamDTO teamDTO, Long teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(EntityNotFoundException::new);
        Team teamFromDTO = fromDTO(teamDTO);

        team.setName(teamFromDTO.getName());
        team.setUsers(teamFromDTO.getUsers());

        return team;
    }
}
