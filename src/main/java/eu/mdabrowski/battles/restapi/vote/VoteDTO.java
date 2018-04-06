package eu.mdabrowski.battles.restapi.vote;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDTO {
    private Long id;

    @NotNull
    Long user;

    @NotNull
    private Integer value;
}
