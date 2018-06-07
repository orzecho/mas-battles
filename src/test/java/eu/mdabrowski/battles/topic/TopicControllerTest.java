package eu.mdabrowski.battles.topic;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.AcceptanceTest;
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
public class TopicControllerTest extends AcceptanceTest {

    private final String URL = "/topics";

    @Test
    public void findAllTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.topics", hasSize(1)))
                .andExpect(jsonPath("$.topics[0].id").exists())
                .andExpect(jsonPath("$.topics[0].value").value("Test topic"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + topic.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").exists())
                .andExpect(jsonPath("$.topic.id").exists())
                .andExpect(jsonPath("$.topic.value").value("Test topic"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + topic.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(topicRepository.findById(topic.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + topic.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"topic\":{\"value\":\"Test2\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").exists())
                .andExpect(jsonPath("$.topic.id").exists())
                .andExpect(jsonPath("$.topic.value").value("Test2"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"topic\":{\"value\":\"Test\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").exists())
                .andExpect(jsonPath("$.topic.id").exists())
                .andExpect(jsonPath("$.topic.value").value("Test"));
    }
}
