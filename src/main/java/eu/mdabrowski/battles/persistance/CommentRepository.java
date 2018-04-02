package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
