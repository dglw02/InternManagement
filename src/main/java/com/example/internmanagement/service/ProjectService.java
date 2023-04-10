package com.example.internmanagement.service;

import com.example.internmanagement.entity.Project;
import com.example.internmanagement.exception.ErrorException;
import com.example.internmanagement.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project saveProject(@Valid Project project) {
        return projectRepository.save(project);
    }

    public void deleteProjectById(Long id) {
        if (id == 0) {
            throw new ErrorException("You need to provide ID of project to be deleted. ID can not be 0.");
        }
        Optional<Project> checkIfProjecttWithIdExist = projectRepository.findById(id);
        if (checkIfProjecttWithIdExist.isEmpty()) {
            throw new ErrorException(
                    "User can not be deleted because project with id: " + id + " does not exist.");
        }
        projectRepository.deleteById(id);
    }
}
