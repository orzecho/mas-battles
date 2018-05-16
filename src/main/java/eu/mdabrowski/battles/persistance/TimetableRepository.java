package eu.mdabrowski.battles.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
}
