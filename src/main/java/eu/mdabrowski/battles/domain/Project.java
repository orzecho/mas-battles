package eu.mdabrowski.battles.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "projects")
public class Project extends BaseEntity implements Votable, Commentable, Taggable {
    @NotBlank
    private String name;

    @NotNull
    private Team team;

    private Set<Vote> votes = new HashSet<>();

    private Set<Comment> comments = new HashSet<>();

    private Set<Tag> tags = new HashSet<>();
}
