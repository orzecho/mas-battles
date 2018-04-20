package eu.mdabrowski.battles;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
import eu.mdabrowski.battles.persistance.VoteRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public abstract class AcceptanceTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ProjectRepository projectRepository;

    @Autowired
    protected TeamRepository teamRepository;

    @Autowired
    protected CommentRepository commentRepository;

    protected User user;
    protected Project project;
    protected Team team;
    protected Tag tag;
    protected Comment comment;

    @Before
    public void init() {
        team = teamRepository.save(Team.builder().name("Test Team").build());
        user = userRepository.save(User.builder().login("Jan").build());
        project = projectRepository.save(Project.builder()
            .name("Test")
            .team(team)
            .build());
        tag = tagRepository.save(Tag.builder()
            .name("Test")
            .build());
        comment = commentRepository.save(Comment.builder()
            .content("Test")
            .user(user)
            .build());
    }

}
