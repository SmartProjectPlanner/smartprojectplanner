package dk.smartprojectplanner.repository;

import dk.smartprojectplanner.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Data access for the project table, using JdbcTemplate.
 */
@Repository
public class ProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Creates the repository.
     *
     * @param jdbcTemplate the JdbcTemplate used to run SQL against the database
     */
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Inserts a new project into the project table. The variable id is generated
     * by the database and the other two variables end date and share token are left null
     *
     * @param project the project to save, with project manager id, title, description,
     *                deadline and start date
     * @return the number of rows inserted, 1 on success
     */
    public int save(Project project) {
        String sql = "INSERT INTO project (project_manager_id, title, description, deadline, start_date) " +
                "VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql, project.getProjectManagerId(), project.getTitle(),
                project.getDescription(), project.getDeadline(), project.getStartDate());
    }
}