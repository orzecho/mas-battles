package eu.mdabrowski.battles.persistance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> getByUserAndProject(User user, Project project);
}
