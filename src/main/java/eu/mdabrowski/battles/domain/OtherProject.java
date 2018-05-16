package eu.mdabrowski.battles.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class OtherProject extends BaseEntity{
    @OneToOne
    @JoinColumn(name="project_id", unique = true, nullable = false)
    Project project;

    private String description;
}
