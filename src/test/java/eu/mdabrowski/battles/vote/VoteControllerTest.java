package eu.mdabrowski.battles.vote;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.AcceptanceTest;
import eu.mdabrowski.battles.domain.Vote;
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

public class VoteControllerTest extends AcceptanceTest {

    private final String URL = "/votes";

    @Test
    public void findAllTest() throws Exception{
        //given
        Vote vote = Vote.builder()
                .value(5)
                .user(user)
                .project(project)
                .build();
        voteRepository.save(vote);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.votes", hasSize(1)))
                .andExpect(jsonPath("$.votes[0].id").exists())
                .andExpect(jsonPath("$.votes[0].user").value(user.getId()))
                .andExpect(jsonPath("$.votes[0].value").value(5));
    }


    @Test
    public void findOneTest() throws Exception{
        //given
        Vote vote = Vote.builder()
                .value(5)
                .user(user)
                .build();
        vote = voteRepository.save(vote);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + vote.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.vote").exists())
                .andExpect(jsonPath("$.vote.id").exists())
                .andExpect(jsonPath("$.vote.user").value(user.getId()))
                .andExpect(jsonPath("$.vote.value").value(5));
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        Vote vote = Vote.builder()
                .value(5)
                .user(user)
                .build();
        vote = voteRepository.save(vote);

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + vote.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(voteRepository.findById(vote.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given
        Vote vote = Vote.builder()
                .value(5)
                .user(user)
                .build();
        vote = voteRepository.save(vote);

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + vote.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"vote\":{\"value\":6, \"user\": " + user.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.vote").exists())
                .andExpect(jsonPath("$.vote.id").exists())
                .andExpect(jsonPath("$.vote.user").value(user.getId()))
                .andExpect(jsonPath("$.vote.value").value(6));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"vote\":{\"value\":5, \"user\": " + user.getId() + "}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.vote").exists())
                .andExpect(jsonPath("$.vote.id").exists())
                .andExpect(jsonPath("$.vote.user").value(user.getId()))
                .andExpect(jsonPath("$.vote.value").value(5));
    }
}
