package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
