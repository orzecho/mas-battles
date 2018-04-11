package eu.mdabrowski.battles.team;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.persistance.TeamRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class TeamControllerTest {

    private final String URL = "/teams";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void findAllTest() throws Exception{
        //given
        Team team = Team.builder()
                .name("Test")
                .build();
        teamRepository.save(team);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.teams", hasSize(1)))
                .andExpect(jsonPath("$.teams[0].id").exists())
                .andExpect(jsonPath("$.teams[0].name").value("Test"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given
        Team team = Team.builder()
                .name("Test")
                .build();
        team = teamRepository.save(team);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + team.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.team").exists())
                .andExpect(jsonPath("$.team.id").exists())
                .andExpect(jsonPath("$.team.name").value("Test"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Team team = Team.builder()
                .name("Test")
                .build();
        team = teamRepository.save(team);

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + team.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(teamRepository.findById(team.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Team team = Team.builder()
                .name("Test")
                .build();
        team = teamRepository.save(team);

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + team.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"team\":{\"name\":\"Test2\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.team").exists())
                .andExpect(jsonPath("$.team.id").exists())
                .andExpect(jsonPath("$.team.name").value("Test2"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"team\":{\"name\":\"Test\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.team").exists())
                .andExpect(jsonPath("$.team.id").exists())
                .andExpect(jsonPath("$.team.name").value("Test"));
    }
}
