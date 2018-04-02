package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
