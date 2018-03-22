package eu.mdabrowski.battles.domain;

import java.util.Set;

public interface Votable {
    Set<Vote> getVotes();
}
