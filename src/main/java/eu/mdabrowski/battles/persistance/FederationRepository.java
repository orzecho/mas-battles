package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Federation;

@Repository
public interface FederationRepository extends JpaRepository<Federation, Long> {
}
