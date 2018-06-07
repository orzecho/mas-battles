package eu.mdabrowski.battles.restapi.topic;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicDTO {
    private Long id;

    @NotNull
    private String value;

    private Integer numberOfVotes;

    private Double meanVote;

    private Set<Long> comments;

    private Set<Long> battles;
}
