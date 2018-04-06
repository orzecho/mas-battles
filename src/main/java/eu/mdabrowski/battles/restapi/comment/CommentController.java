package eu.mdabrowski.battles.restapi.comment;

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

import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseListWrapper<CommentDTO> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDTO> commentDTOs = comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(commentDTOs, "comments");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<CommentDTO> getComment(@PathVariable Long id) {
        return new ResponseWrapper<>(commentRepository
                .findById(id)
                .map(commentMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "comment");
    }

    @PostMapping
    public ResponseWrapper<CommentDTO> createComment(@Valid @RequestBody Map<String, CommentDTO> commentDTO) {
        Comment comment = commentMapper.fromDTO(commentDTO.get("comment"));
        Comment savedComment = commentRepository.save(comment);
        CommentDTO savedCommentDTO = commentMapper.toDTO(savedComment);
        return new ResponseWrapper<>(savedCommentDTO, "comment");
    }

    @PutMapping("/{id}")
    public ResponseWrapper<CommentDTO> updateComment(@PathVariable Long id, @Valid @RequestBody Map<String, CommentDTO>
            projectDTO) {
        Comment oldComment = commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Comment updatedComment = commentRepository.save(commentMapper.update(projectDTO.get("comment"), oldComment));
        return new ResponseWrapper<>(commentMapper.toDTO(updatedComment), "comment");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
