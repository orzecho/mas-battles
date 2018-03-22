package eu.mdabrowski.battles.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "votes")
public class Vote extends BaseEntity {

    private User user;

    private Integer value;
}
