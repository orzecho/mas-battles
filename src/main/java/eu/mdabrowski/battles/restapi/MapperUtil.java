package eu.mdabrowski.battles.restapi;

import eu.mdabrowski.battles.domain.BaseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MapperUtil {

    public static Set<Long> setToIds(Set<? extends BaseEntity> baseEntities) {
        return Optional.ofNullable(baseEntities).orElse(new HashSet<>()).stream()
                .map(BaseEntity::getId).collect(Collectors.toSet());
    }
}
