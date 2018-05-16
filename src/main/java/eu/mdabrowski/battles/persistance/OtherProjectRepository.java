package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.OtherProject;

@Repository
public interface OtherProjectRepository extends JpaRepository<OtherProject, Long> {
}
