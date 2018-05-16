package eu.mdabrowski.battles.domain;

public interface TimedEvent {
    Timetable getTimetable();

    default boolean isValid(){
        return true;
    }
}
