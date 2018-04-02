package eu.mdabrowski.battles.restapi.project;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.mdabrowski.battles.domain.Project;
import eu.mdabrowski.battles.persistance.ProjectRepository;
import eu.mdabrowski.battles.persistance.TeamRepository;
import eu.mdabrowski.battles.restapi.wrapper.ResponseListWrapper;
import eu.mdabrowski.battles.restapi.wrapper.ResponseWrapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @GetMapping
    public ResponseListWrapper<ProjectDTO> getProjects() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> projectDTOs = projects.stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseListWrapper<>(projectDTOs, "projects");
    }

    @GetMapping("/{id}")
    public ResponseWrapper<ProjectDTO> getProject(@PathVariable Long id) {
        return new ResponseWrapper<>(projectRepository
                .findById(id)
                .map(projectMapper::toDTO)
                .orElseThrow(EntityNotFoundException::new), "project");
    }

    @PostMapping
    public ResponseWrapper<ProjectDTO> createProject(@Valid @RequestBody Map<String, ProjectDTO> projectDTO) {
        Project project = projectMapper.fromDTO(projectDTO.get("project"));
        Project savedProject = projectRepository.save(project);
        ProjectDTO savedProjectDTO = projectMapper.toDTO(savedProject);
        return new ResponseWrapper<>(savedProjectDTO, "project");
    }

    @PutMapping("/{id}")
    public ResponseWrapper<ProjectDTO> updateProject(@PathVariable Long id, @Valid @RequestBody Map<String, ProjectDTO>
            projectDTO) {
        Project oldProject = projectRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Project updatedProject = projectRepository.save(projectMapper.update(projectDTO.get("project"), oldProject));
        return new ResponseWrapper<>(projectMapper.toDTO(updatedProject), "project");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProject(@PathVariable Long id) {
        projectRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
