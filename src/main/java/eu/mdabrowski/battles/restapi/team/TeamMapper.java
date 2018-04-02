package eu.mdabrowski.battles.restapi.team;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

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

    public TeamDTO toDTO(Team team) {
        return TeamDTO.builder()
                .id(team.getId())
                .name(team.getName())
                .users(setToIds(team.getUsers()))
                .build();
    }

    public Team fromDTO(TeamDTO teamDTO) {
        Team team = Team.builder()
                .name(teamDTO.getName())
                .users(getUsersFromIds(teamDTO.getUsers(), userRepository))
                .build();
        return team;
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
        team.setUsers(teamFromDTO.getUsers());

        return team;
    }
}
