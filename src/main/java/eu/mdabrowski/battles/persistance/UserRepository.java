package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
