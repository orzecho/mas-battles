package eu.mdabrowski.battles.restapi.tag;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagMapper {

    private final TagRepository tagRepository;

    public TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public Tag fromDTO(TagDTO tagDTO) {
        return Tag.builder()
                .name(tagDTO.getName())
                .build();
    }
    
    public Tag update(TagDTO tagDTO, Tag tag) {
        Tag tagFromDTO = fromDTO(tagDTO);

        tag.setName(tagFromDTO.getName());

        return tag;
    }

    //MAS
    public Tag update(TagDTO tagDTO, Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(EntityNotFoundException::new);
        Tag tagFromDTO = fromDTO(tagDTO);

        tag.setName(tagFromDTO.getName());

        return tag;
    }
}
