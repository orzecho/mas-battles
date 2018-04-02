package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
