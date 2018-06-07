package eu.mdabrowski.battles.domain;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Team extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(max = 255)
    private String name;

    @OneToMany
    private Set<User> users = new HashSet<>();

    @ManyToOne
    private User leader;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FederatedTeam> federatedTeams;

    @AssertTrue
    @Transient
    private boolean isLeaderInUsers() {

        return users != null ? users.contains(leader) : leader == null;
    }

    //MAS ordered
    @Transient
    private List<Federation> getHistoryOfFederationsByStartDate() {
        return federatedTeams.stream()
                .sorted(Comparator.comparing(FederatedTeam::getStartDate))
                .map(FederatedTeam::getFederation)
                .collect(Collectors.toList());
    }

    @PreRemove
    private void preRemove() {
        users.forEach((e -> e.setTeam(null)));
    }
}
