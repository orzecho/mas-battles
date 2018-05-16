package eu.mdabrowski.battles.battle;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import eu.mdabrowski.battles.AcceptanceTest;
import eu.mdabrowski.battles.domain.Team;
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

public class BattleControllerTest extends AcceptanceTest {

    private final String URL = "/battles";

    @Test
    public void findAllTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL).contentType
                (MediaType
                .APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.battles", hasSize(1)))
                .andExpect(jsonPath("$.battles[0].id").exists())
                .andExpect(jsonPath("$.battles[0].battleStatus").value("NEW"));
    }


    @Test
    public void findOneTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "//" + battle.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.battle").exists())
                .andExpect(jsonPath("$.battle.id").exists())
                .andExpect(jsonPath("$.battle.battleStatus").value("NEW"));
    }

    @Test
    public void deleteTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "//" + battle.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk());
        assertThat(battleRepository.findById(battle.getId()).isPresent()).isFalse();
    }

    @Test
    public void updateTest() throws Exception{
        //given

        //when
        ResultActions resultActions = mockMvc.perform(put(URL + "//" + battle.getId())
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"battle\":{\"battleStatus\":\"VOTING\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.battle").exists())
                .andExpect(jsonPath("$.battle.id").exists())
                .andExpect(jsonPath("$.battle.battleStatus").value("VOTING"));
    }

    @Test
    public void createTest() throws Exception{
        //given
        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .with(csrf())
                .with(user("test").roles("BATTLE_USER"))
                .content("{\"battle\":{\"battleStatus\":\"NEW\"}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.battle").exists())
                .andExpect(jsonPath("$.battle.id").exists())
                .andExpect(jsonPath("$.battle.battleStatus").value("NEW"));
    }
}
