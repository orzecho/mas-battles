package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
