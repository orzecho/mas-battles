package eu.mdabrowski.battles.restapi.user;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.restapi.team.TeamMapper;
import eu.mdabrowski.battles.restapi.user.UserDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .team(user.getTeam().map(BaseEntity::getId).orElse(null))
                .roles(Optional.ofNullable(user.getUserRoles()).map(roles -> roles.stream().map(e -> e.getRole()
                        .getName()).collect(Collectors.toList())).orElse(null))
                .build();
    }
}
