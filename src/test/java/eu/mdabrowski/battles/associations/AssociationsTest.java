package eu.mdabrowski.battles.associations;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Vote;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class AssociationsTest {

    @Autowired
    private AssociationService associationService;

    @Test
    public void classAssociationsTest() {
        //given
        Project project = Project.builder()
                .name("Project 1")
                .build();
        Vote vote1 = Vote.builder()
                .value(5)
                .build();
        Vote vote2 = Vote.builder()
                .value(3)
                .build();
        Set<Vote> votes = new HashSet<>();
        votes.add(vote1);
        votes.add(vote2);
        project.setVotes(votes);

        //then
        log.info(associationService.getAssociationsAsString(Project.class));
        log.info(associationService.getAssociationsAsString(project));
    }

    @Test
    public void validationTest() {
        Project project = Project.builder()
                .name("Project 1")
                .build();
        Vote vote1 = Vote.builder()
                .value(5)
                .build();
        Vote vote2 = Vote.builder()
                .value(3)
                .build();
        Vote vote3 = Vote.builder()
                .value(7)
                .build();
        Set<Vote> votes = new HashSet<>();
        votes.add(vote1);
        votes.add(vote2);
        project.setVotes(votes);
        assertThat(associationService.validate(project)).isTrue();
        votes.add(vote3);
        assertThat(associationService.validate(project)).isFalse();
    }
}
