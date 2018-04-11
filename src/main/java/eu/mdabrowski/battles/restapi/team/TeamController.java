package eu.mdabrowski.battles.restapi.team;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.domain.Team;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamController {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseListWrapper<TeamDTO> getTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamDTO> teamDTOs = teams.stream()
                .map(teamMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(teamDTOs, "teams");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<TeamDTO> getTeam(@PathVariable Long id) {
        return new ResponseWrapper<>(teamRepository
                .findById(id)
                .map(teamMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "team");
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TeamDTO> createTeam(@Valid @RequestBody Map<String, TeamDTO> teamDTO) {
        Team team = teamMapper.fromDTO(teamDTO.get("team"));
        Team savedTeam = teamRepository.save(team);
        TeamDTO savedTeamDTO = teamMapper.toDTO(savedTeam);
        return new ResponseWrapper<>(savedTeamDTO, "team");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<TeamDTO> updateTeam(@PathVariable Long id, @Valid @RequestBody Map<String, TeamDTO>
            projectDTO) {
        Team oldTeam = teamRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Team updatedTeam = teamRepository.save(teamMapper.update(projectDTO.get("team"), oldTeam));
        return new ResponseWrapper<>(teamMapper.toDTO(updatedTeam), "team");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseEntity deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
