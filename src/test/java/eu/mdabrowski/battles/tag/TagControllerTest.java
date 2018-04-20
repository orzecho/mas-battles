package eu.mdabrowski.battles.tag;

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

import eu.mdabrowski.battles.AcceptanceTest;
import eu.mdabrowski.battles.domain.Tag;
import eu.mdabrowski.battles.persistance.TagRepository;
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
public class TagControllerTest extends AcceptanceTest {

    private final String URL = "/tags";

    @Test
    public void findAllTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.tags", hasSize(1)))
                .andExpect(jsonPath("$.tags[0].id").exists())
                .andExpect(jsonPath("$.tags[0].name").value("Test"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + tag.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").exists())
                .andExpect(jsonPath("$.tag.id").exists())
                .andExpect(jsonPath("$.tag.name").value("Test"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + tag.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(tagRepository.findById(tag.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + tag.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"tag\":{\"name\":\"Test2\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").exists())
                .andExpect(jsonPath("$.tag.id").exists())
                .andExpect(jsonPath("$.tag.name").value("Test2"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"tag\":{\"name\":\"Test\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.tag").exists())
                .andExpect(jsonPath("$.tag.id").exists())
                .andExpect(jsonPath("$.tag.name").value("Test"));
    }
}
