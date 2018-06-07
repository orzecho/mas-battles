package eu.mdabrowski.battles.restapi.user;

import java.security.Principal;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.restapi.topic.TopicDTO;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import eu.mdabrowski.battles.security.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users/{id}")
    public ResponseWrapper<UserDTO> getUser(@PathVariable Long id) {
        return new ResponseWrapper<>(userRepository
                .findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "user");
    }

    @GetMapping("/cusers/1")
    public ResponseWrapper<UserDTO> getCurrentUser(Principal principal) {
        return new ResponseWrapper<>(userMapper.toDTO(userService.getCurrentUser(principal)), "cuser");
    }

    @GetMapping("/join-team/{id}")
    public ResponseWrapper<UserDTO> joinTeam(Principal principal, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser(principal);
        userService.joinTeam(currentUser, id);
        return new ResponseWrapper<>(userMapper.toDTO(currentUser), "user");
    }

    @GetMapping("/leave-team/{id}")
    public ResponseWrapper<UserDTO> leaveTeam(Principal principal, @PathVariable Long id) {
        User currentUser = userService.getCurrentUser(principal);
        userService.leaveTeam(currentUser, id);
        return new ResponseWrapper<>(userMapper.toDTO(currentUser), "user");
    }
}
