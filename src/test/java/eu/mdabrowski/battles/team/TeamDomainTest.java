package eu.mdabrowski.battles.team;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.DubbingProjectRepository;
import eu.mdabrowski.battles.persistance.OtherProjectRepository;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class TeamDomainTest {

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


    @Test(expected = Exception.class)
    public void testUniqueConstraint() {
        Team team = teamRepository.save(Team.builder()
                .name("test team")
                .build());

        Team team2 = teamRepository.save(Team.builder()
                .name("test team")
                .build());


        teamRepository.findAll();
    }

    @Test(expected = Exception.class)
    public void testSubsetLeaderInTeam() {
        Team team = teamRepository.save(Team.builder()
                .name("test team")
                .build());

        User user = userRepository.save(User.builder()
                .login("Leader")
                .build());

        team.setLeader(user);

        teamRepository.findAll();
    }

    @Test(expected = Exception.class)
    public void testTeamNameSizez() {
        Team team = teamRepository.save(Team.builder()
                .name("test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!test team!")
                .build());

        teamRepository.findAll();
    }
}
