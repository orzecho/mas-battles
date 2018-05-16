package eu.mdabrowski.battles.comment;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.AcceptanceTest;
import eu.mdabrowski.battles.domain.Comment;
import eu.mdabrowski.battles.domain.User;
import eu.mdabrowski.battles.persistance.CommentRepository;
import eu.mdabrowski.battles.persistance.UserRepository;
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

public class CommentControllerTest extends AcceptanceTest {

    private final String URL = "/comments";

    @Before
    public void init() {
        user = userRepository.save(User.builder().login("Jan").build());
        comment = commentRepository.save(Comment.builder()
                .content("Test")
                .user(user)
                .build());
    }

    @Test
    public void findAllTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(1)))
                .andExpect(jsonPath("$.comments[0].id").exists())
                .andExpect(jsonPath("$.comments[0].user").value(user.getId()))
                .andExpect(jsonPath("$.comments[0].content").value("Test"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + comment.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").exists())
                .andExpect(jsonPath("$.comment.id").exists())
                .andExpect(jsonPath("$.comment.user").value(user.getId()))
                .andExpect(jsonPath("$.comment.content").value("Test"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + comment.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(commentRepository.findById(comment.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + comment.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"comment\":{\"content\":\"Test2\", \"user\": " + user.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").exists())
                .andExpect(jsonPath("$.comment.id").exists())
                .andExpect(jsonPath("$.comment.user").value(user.getId()))
                .andExpect(jsonPath("$.comment.content").value("Test2"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"comment\":{\"content\":\"Test\", \"user\": " + user.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.comment").exists())
                .andExpect(jsonPath("$.comment.id").exists())
                .andExpect(jsonPath("$.comment.user").value(user.getId()))
                .andExpect(jsonPath("$.comment.content").value("Test"));
    }
}
