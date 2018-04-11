package eu.mdabrowski.battles.restapi.tag;

import java.util.List;
import java.util.Map;
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

import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseListWrapper<TagDTO> getTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDTO> tagDTOs = tags.stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(tagDTOs, "tags");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<TagDTO> getTag(@PathVariable Long id) {
        return new ResponseWrapper<>(tagRepository
                .findById(id)
                .map(tagMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "tag");
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TagDTO> createTag(@Valid @RequestBody Map<String, TagDTO> tagDTO) {
        Tag tag = tagMapper.fromDTO(tagDTO.get("tag"));
        Tag savedTag = tagRepository.save(tag);
        TagDTO savedTagDTO = tagMapper.toDTO(savedTag);
        return new ResponseWrapper<>(savedTagDTO, "tag");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TagDTO> updateTag(@PathVariable Long id, @Valid @RequestBody Map<String, TagDTO>
            projectDTO) {
        Tag oldTag = tagRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Tag updatedTag = tagRepository.save(tagMapper.update(projectDTO.get("tag"), oldTag));
        return new ResponseWrapper<>(tagMapper.toDTO(updatedTag), "tag");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseEntity deleteTag(@PathVariable Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
