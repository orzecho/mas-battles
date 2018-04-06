package eu.mdabrowski.battles.restapi.tag;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private Long id;

    @NotNull
    private String name;
}
