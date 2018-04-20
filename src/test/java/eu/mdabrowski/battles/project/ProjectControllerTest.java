package eu.mdabrowski.battles.project;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.AcceptanceTest;
import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.persistance.ProjectRepository;
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

public class ProjectControllerTest extends AcceptanceTest {

    private final String URL = "/projects";

    @Before
    public void setup() {
        team = teamRepository.save(Team.builder().name("Testowa drużyna").build());
    }

    @Test
    public void findAllTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType
                (MediaType
                .APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.projects", hasSize(1)))
                .andExpect(jsonPath("$.projects[0].id").exists())
                .andExpect(jsonPath("$.projects[0].name").value("Test"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + project.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.project").exists())
                .andExpect(jsonPath("$.project.id").exists())
                .andExpect(jsonPath("$.project.name").value("Test"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + project.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(projectRepository.findById(project.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + project.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"project\":{\"name\":\"Test2\", \"team\": " + team.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.project").exists())
                .andExpect(jsonPath("$.project.id").exists())
                .andExpect(jsonPath("$.project.name").value("Test2"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"project\":{\"name\":\"Test\",\"team\":" + team.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.project").exists())
                .andExpect(jsonPath("$.project.id").exists())
                .andExpect(jsonPath("$.project.name").value("Test"));
    }
}
