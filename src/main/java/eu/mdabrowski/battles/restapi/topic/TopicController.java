package eu.mdabrowski.battles.restapi.topic;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.Topic;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.TopicRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;
import eu.mdabrowski.battles.restapi.topic.TopicDTO;
import eu.mdabrowski.battles.restapi.topic.TopicMapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import eu.mdabrowski.battles.security.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/topics")
public class TopicController {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final UserService userService;
    private final VoteRepository voteRepository;

    @GetMapping
    public ResponseListWrapper<TopicDTO> getTopics() {
        List<Topic> topics = topicRepository.findAll();
        List<TopicDTO> topicDTOs = topics.stream()
                .map(topicMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(topicDTOs, "topics");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<TopicDTO> getTopic(@PathVariable Long id) {
        return new ResponseWrapper<>(topicRepository
                .findById(id)
                .map(topicMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "topic");
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TopicDTO> createTopic(@Valid @RequestBody Map<String, TopicDTO> topicDTO) {
        Topic topic = topicMapper.fromDTO(topicDTO.get("topic"));
        Topic savedTopic = topicRepository.save(topic);
        TopicDTO savedTopicDTO = topicMapper.toDTO(savedTopic);
        return new ResponseWrapper<>(savedTopicDTO, "topic");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TopicDTO> updateTopic(@PathVariable Long id, @Valid @RequestBody Map<String, TopicDTO>
            projectDTO) {
        Topic oldTopic = topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Topic updatedTopic = topicRepository.save(topicMapper.update(projectDTO.get("topic"), oldTopic));
        return new ResponseWrapper<>(topicMapper.toDTO(updatedTopic), "topic");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TopicDTO> deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        ResponseWrapper<TopicDTO> topicResponse = new ResponseWrapper<>(topicMapper.toDTO(topic), "topic");
        topicRepository.delete(topic);
        return topicResponse;
    }

    @GetMapping("/vote/{id}/{value}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TopicDTO> vote(Principal principal, @PathVariable Long id, @PathVariable Double value) {
        User currentUser = userService.getCurrentUser(principal);
        Topic topic = topicRepository.getOne(id);
        if(topic.getVotes().stream().anyMatch(e -> e.getUser().equals(currentUser))) {
            throw new RuntimeException();
        }

        Vote vote = Vote.builder()
            .user(currentUser)
            .value(value)
            .topic(topic)
            .build();

        topic.getVotes().add(vote);
        voteRepository.save(vote);
        topicRepository.save(topic);
        return new ResponseWrapper<>(topicMapper.toDTO(topic), "topic");
    }
}
