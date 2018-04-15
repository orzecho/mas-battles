package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
