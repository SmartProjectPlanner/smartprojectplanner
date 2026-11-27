package dk.smartprojectplanner.repository;

import dk.smartprojectplanner.model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

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

    /**
     * Finds all projects owned by the given project manager.
     *
     * @param projectManagerId the id of the project manager
     * @return the projects of that manager, or an empty list if there are none
     */
    public List<Project> findByProjectManagerId(int projectManagerId) {
        String sql = "SELECT id, project_manager_id, title, description, deadline," +
                "start_date, end_date, share_token FROM project WHERE project_manager_id = ?";
        return jdbcTemplate.query(sql, this::mapProject, projectManagerId);
    }

    /**
     * Maps one row of the project table to a Project. Dates are read with getObject,
     * so that a null end date stays null instead of throwing.
     *
     * @param rs            the result set, positioned on the row to map
     * @param rowNum        the number of the current row, not used here
     * @return              a Project built from the columns of the current row
     * @throws SQLException if a column can't be read from the result set
     */
    private Project mapProject(ResultSet rs, int rowNum) throws SQLException {
        return new Project(
                rs.getInt("id"),
                rs.getInt("project_manager_id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getObject("deadline", LocalDate.class),
                rs.getObject("start_date", LocalDate.class),
                rs.getObject("end_date", LocalDate.class),
                rs.getString("share_token"));
    }
}