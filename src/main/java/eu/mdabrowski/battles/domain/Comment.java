package eu.mdabrowski.battles.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String content;

    @ManyToOne
    @JoinColumn( name = "battle_id")
    private Battle battle;

    @ManyToOne
    @JoinColumn( name = "topic_id")
    private Topic topic;
}
