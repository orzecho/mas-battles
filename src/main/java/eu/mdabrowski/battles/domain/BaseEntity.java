package eu.mdabrowski.battles.domain;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createTs;

    private LocalDateTime updateTs;

    @PrePersist
    protected void onPersist() {
        createTs = LocalDateTime.now();
        updateTs = createTs;
    }

    @PreUpdate
    protected void onUpdate() {
        updateTs = LocalDateTime.now();
    }
}
