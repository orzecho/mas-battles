package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.DubbingProject;

@Repository
public interface DubbingProjectRepository extends JpaRepository<DubbingProject, Long> {
}
