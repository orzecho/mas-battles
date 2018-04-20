package eu.mdabrowski.battles.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Role extends BaseEntity{
    private String name;

    @OneToMany
    private Set<UserRole> userRoles;
}
