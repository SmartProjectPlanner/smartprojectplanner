package dk.smartprojectplanner.service;

import dk.smartprojectplanner.repository.ProjectManagerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectManagerService {

    private final ProjectManagerRepository projectManagerRepository;

    public ProjectManagerService(ProjectManagerRepository projectManagerRepository) {
        this.projectManagerRepository = projectManagerRepository;
    }

    public void register() {

    }

    private void validateUser() {

    }


}
