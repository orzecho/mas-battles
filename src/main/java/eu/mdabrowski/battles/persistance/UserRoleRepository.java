package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
