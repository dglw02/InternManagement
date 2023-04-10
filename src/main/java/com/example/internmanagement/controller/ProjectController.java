package com.example.internmanagement.controller;

import com.example.internmanagement.entity.Project;
import com.example.internmanagement.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> saveSubject(@Valid @RequestBody Project project) {
        Project newProject = projectService.saveProject(project);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newProject.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProjectById(@PathVariable("id") Long id) {
        projectService.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
