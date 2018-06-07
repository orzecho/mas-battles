package eu.mdabrowski.battles.restapi.topic;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.BaseEntity;
import eu.mdabrowski.battles.domain.Topic;
import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.TopicRepository;
import eu.mdabrowski.battles.restapi.topic.TopicDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TopicMapper {

    private final TopicRepository topicRepository;

    public TopicDTO toDTO(Topic topic) {
        return TopicDTO.builder()
                .id(topic.getId())
                .value(topic.getValue())
                .numberOfVotes(Optional.ofNullable(topic.getVotes()).map(Set::size).orElse(null))
                .meanVote(Optional.ofNullable(topic.getVotes()).map(e -> e.stream().mapToDouble(Vote::getValue)
                        .average()
                        .orElse(0.0)).orElse(0.0))
                .battles(Optional.ofNullable(topic.getBattles())
                        .map(e -> e.stream()
                            .map(BaseEntity::getId).collect(Collectors.toSet())).orElse(null))
                .comments(Optional.ofNullable(topic.getComments())
                        .map(e -> e.stream()
                                .map(BaseEntity::getId).collect(Collectors.toSet())).orElse(null))
                .build();
    }

    public Topic fromDTO(TopicDTO topicDTO) {
        return Topic.builder()
                .value(topicDTO.getValue())
                .build();
    }
    
    public Topic update(TopicDTO topicDTO, Topic topic) {
        Topic topicFromDTO = fromDTO(topicDTO);

        topic.setValue(topicFromDTO.getValue());

        return topic;
    }
}
