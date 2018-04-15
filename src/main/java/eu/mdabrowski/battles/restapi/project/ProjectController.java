package eu.mdabrowski.battles.restapi.project;

import java.security.Principal;
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
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final TeamRepository teamRepository;

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseListWrapper<ProjectDTO> getProjects(Principal principal) {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(projectDTOs, Project.LABEL_PLURAL);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseWrapper<ProjectDTO> getProject(@PathVariable Long id) {
        return new ResponseWrapper<>(projectRepository
                .findById(id)
                .map(projectMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), Project.LABEL_SINGULAR);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseWrapper<ProjectDTO> createProject(@Valid @RequestBody Map<String, ProjectDTO> projectDTO) {
        Project project = projectMapper.fromDTO(projectDTO.get(Project.LABEL_SINGULAR));
        Project savedProject = projectRepository.save(project);
        ProjectDTO savedProjectDTO = projectMapper.toDTO(savedProject);
        return new ResponseWrapper<>(savedProjectDTO, Project.LABEL_SINGULAR);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    @PreAuthorize("isAuthenticated()")
    public ResponseWrapper<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody Map<String, ProjectDTO>
            projectDTO, Principal principal) {
        Project oldProject = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Project updatedProject = projectRepository.save(projectMapper.update(projectDTO.get(Project.LABEL_SINGULAR), oldProject));
        return new ResponseWrapper<>(projectMapper.toDTO(updatedProject), Project.LABEL_SINGULAR);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_BATTLE_USER')")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    @PreAuthorize("permitAll()")
    public boolean test() {
        Team team = teamRepository.save(Team.builder().name("Testowa drużyna").build());
        Project project = Project.builder().name("Test").team(team).build();
        projectRepository.save(project);
        return true;
    }
}
