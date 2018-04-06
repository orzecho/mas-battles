package eu.mdabrowski.battles.restapi.vote;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.VoteRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/votes")
public class VoteController {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;

    @GetMapping
    public ResponseListWrapper<VoteDTO> getVotes() {
        List<Vote> votes = voteRepository.findAll();
        List<VoteDTO> voteDTOs = votes.stream()
                .map(voteMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(voteDTOs, "votes");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<VoteDTO> getVote(@PathVariable Long id) {
        return new ResponseWrapper<>(voteRepository
                .findById(id)
                .map(voteMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "vote");
    }

    @PostMapping
    public ResponseWrapper<VoteDTO> createVote(@Valid @RequestBody Map<String, VoteDTO> voteDTO) {
        Vote vote = voteMapper.fromDTO(voteDTO.get("vote"));
        Vote savedVote = voteRepository.save(vote);
        VoteDTO savedVoteDTO = voteMapper.toDTO(savedVote);
        return new ResponseWrapper<>(savedVoteDTO, "vote");
    }

    @PutMapping("/{id}")
    public ResponseWrapper<VoteDTO> updateVote(@PathVariable Long id, @Valid @RequestBody Map<String, VoteDTO>
            projectDTO) {
        Vote oldVote = voteRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Vote updatedVote = voteRepository.save(voteMapper.update(projectDTO.get("vote"), oldVote));
        return new ResponseWrapper<>(voteMapper.toDTO(updatedVote), "vote");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVote(@PathVariable Long id) {
        voteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
