package eu.mdabrowski.battles.security;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Set<GrantedAuthority> getGrantedAuthorities(String username) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_BATTLE_USER"));
        return authorities;
    }

    public User getCurrentUser(Principal principal) {
        String login = Optional.ofNullable(principal)
                .orElseThrow(() -> new org.springframework.security.access.AccessDeniedException("Odmowa dostępu."))
                .getName();
        return userRepository.findByLogin(login).orElseGet(() -> createNewUser(login));
    }

    private User createNewUser(String login) {
        User user = User.builder().login(login).build();
        //TODO give me some basic role
        return userRepository.save(user);
    }

    public void joinTeam(User currentUser, Long id) {
        Team team = teamRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        team.getUsers().add(currentUser);
        currentUser.setTeam(team);
        teamRepository.save(team);
        userRepository.save(currentUser);
    }

    public void joinTeam(User currentUser, Team team) {
        team.getUsers().add(currentUser);
        currentUser.setTeam(team);
    }

    public void leaveTeam(User currentUser, Long id) {
        Team team = teamRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        team.getUsers().remove(currentUser);
        if(team.getUsers().isEmpty()) {
            currentUser.setTeam(null);
            userRepository.save(currentUser);
            teamRepository.delete(team);
        } else {
            if (team.getLeader().getId().equals(currentUser.getId())) {
                team.setLeader(team.getUsers().iterator().next());
            }
            teamRepository.save(team);
            currentUser.setTeam(null);
            userRepository.save(currentUser);
        }
    }
}
