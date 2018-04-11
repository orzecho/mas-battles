package eu.mdabrowski.battles.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import eu.mdabrowski.battles.persistance.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
//@RequiredArgsConstructor
public class UserService {
//    private final UserRepository userRepository;

    public Set<GrantedAuthority> getGrantedAuthorities(String username) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
