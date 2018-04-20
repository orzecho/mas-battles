package eu.mdabrowski.battles.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class FederatedTeam extends BaseEntity {
    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="federation_id")
    private Federation federation;
}
