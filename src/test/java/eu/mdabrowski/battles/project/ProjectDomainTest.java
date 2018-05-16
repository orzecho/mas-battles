package eu.mdabrowski.battles.project;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.mdabrowski.battles.domain.DubbingProject;
import eu.mdabrowski.battles.domain.OtherProject;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.domain.Vote;
import eu.mdabrowski.battles.persistance.DubbingProjectRepository;
import eu.mdabrowski.battles.persistance.OtherProjectRepository;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ProjectDomainTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private DubbingProjectRepository dubbingProjectRepository;

    @Autowired
    private OtherProjectRepository otherProjectRepository;


    @Test
    public void cascadeDeletionTest() {
        Team team = teamRepository.save(Team.builder()
                .name("test team")
                .build());

        User user = userRepository.save(User.builder()
                .login("test")
                .team(team)
                .build());

        Project project = projectRepository.save(Project.builder()
            .name("test project")
            .team(team)
            .votes(new HashSet<>())
            .build());

        Vote vote = voteRepository.save(Vote.builder()
                .value(5)
                .user(user)
                .project(project)
                .build());
        project.addVote(vote);

        assertThat(projectRepository.findAll().get(0).getVotes().iterator().next()).isEqualTo(vote);

        projectRepository.delete(project);

        assertThat(voteRepository.findAll()).isEmpty();
    }

    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void testVoteWithoutVotable() {
        Team team = teamRepository.save(Team.builder()
                .name("test team")
                .build());

        User user = userRepository.save(User.builder()
                .login("test")
                .team(team)
                .build());

        Vote vote = voteRepository.save(Vote.builder()
                .value(5)
                .user(user)
                .build());

        voteRepository.findAll();
    }

    @Test(expected = Exception.class)
    public void testDubbingProjectWithoutProject() {
        dubbingProjectRepository.save(DubbingProject.builder()
                .original("Test original")
                .build());

        dubbingProjectRepository.findAll();
    }

    @Test
    public void testDubbingToOtherProject() {
        Team team = teamRepository.save(Team.builder()
                .name("test team")
                .build());

        Project project = projectRepository.save(Project.builder()
                .name("test project")
                .team(team)
                .votes(new HashSet<>())
                .build());

        DubbingProject dubbingProject = dubbingProjectRepository.save(DubbingProject.builder()
                .project(project)
                .original("Dubbing original")
                .build());

        project.setDubbingProject(dubbingProject);

        OtherProject otherProject = otherProjectRepository.save(OtherProject.builder().description("test")
                .project(project).build());

        project.setOtherProject(otherProject);
        project.setDubbingProject(null);
        dubbingProjectRepository.delete(dubbingProject);

        projectRepository.findAll();
    }

}
