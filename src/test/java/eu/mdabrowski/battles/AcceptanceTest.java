package eu.mdabrowski.battles;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import eu.mdabrowski.battles.domain.Battle;
import eu.mdabrowski.battles.domain.BattleStatus;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.domain.Topic;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.BattleRepository;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TagRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.persistance.TopicRepository;
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

    @Autowired
    protected BattleRepository battleRepository;

    @Autowired
    protected TopicRepository topicRepository;


    protected User user;
    protected Project project;
    protected Team team;
    protected Tag tag;
    protected Comment comment;
    protected Battle battle;
    protected Topic topic;

    @Before
    public void init() {
        topic = topicRepository.save(Topic.builder().value("Test topic").build());
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
        Set<Project> projects = new HashSet<>();
        projects.add(project);
        Set<Team> teams = new HashSet<>();
        teams.add(team);
        battle = battleRepository.save(Battle.builder()
                .projects(projects)
                .battleStatus(BattleStatus.NEW)
                .teams(teams)
                .topic(topic)
                .build());
        Set<Battle> battles = new HashSet<>();
        battles.add(battle);
        topic.setBattles(battles);
    }

}
