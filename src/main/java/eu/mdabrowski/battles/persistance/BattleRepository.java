package eu.mdabrowski.battles.persistance;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Team;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {

    List<Battle> findAllByTeamsContaining(Team team);
}
