package eu.mdabrowski.battles.restapi.vote;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VoteMapper {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public VoteDTO toDTO(Vote vote) {
        return VoteDTO.builder()
                .id(vote.getId())
                .value(vote.getValue())
                .user(vote.getUser().getId())
                .build();
    }

    public Vote fromDTO(VoteDTO voteDTO) {
        return Vote.builder()
                .value(voteDTO.getValue())
                .user(getUserById(voteDTO.getUser()))
                .build();
    }

    public Vote update(VoteDTO voteDTO, Vote vote) {
        Vote voteFromDTO = fromDTO(voteDTO);

        vote.setValue(voteFromDTO.getValue());
        vote.setUser(getUserById(voteDTO.getUser()));

        return vote;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }
}
