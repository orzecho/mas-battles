package eu.mdabrowski.battles.restapi.comment;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import static eu.mdabrowski.battles.restapi.MapperUtil.setToIds;
import eu.mdabrowski.battles.restapi.comment.CommentDTO;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;

    public CommentDTO toDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .user(comment.getUser().getId())
                .build();
    }

    public Comment fromDTO(CommentDTO commentDTO) {
        return Comment.builder()
                .user(userRepository.findById(commentDTO.getUser()).orElseThrow(EntityNotFoundException::new))
                .content(commentDTO.getContent())
                .build();
    }

    public Comment update(CommentDTO commentDTO, Comment comment) {
        Comment commentFromDTO = fromDTO(commentDTO);

        comment.setUser(commentFromDTO.getUser());
        comment.setContent(commentFromDTO.getContent());

        return comment;
    }
}
