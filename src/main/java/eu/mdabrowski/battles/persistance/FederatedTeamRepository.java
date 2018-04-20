package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.FederatedTeam;

@Repository
public interface FederatedTeamRepository extends JpaRepository<FederatedTeam, Long> {
}
