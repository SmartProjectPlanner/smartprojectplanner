package dk.smartprojectplanner.repository;

import dk.smartprojectplanner.model.ProjectManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Handles databaseoperations for projectmanagers
 */
@Repository
public class ProjectManagerRepository {

    private static final String FIND_BY_EMAIL = """
            SELECT id, name, email, password
            FROM project_manager
            WHERE email = ?
            """;

    private final JdbcTemplate jdbcTemplate;

    /**
     * Creates the repository with access to the database through JdbcTemplate
     *
     * @param jdbcTemplate handles SQL-queries
     */
    public ProjectManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save() {
        return 1;
    }

    /**
     * Finds a projectmanager by their e-mail
     *
     * @param email the e-mail adress that's being searched for
     * @return an Optional with the ProjectManager, if the e-mail doesn't exist then return an empty Optional
     */
    public Optional<ProjectManager> findByEmail(String email) {
        List<ProjectManager> projectManagers = jdbcTemplate.query(
                FIND_BY_EMAIL,
                (resultSet, rowNum) -> new ProjectManager(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                ),
                email
        );

        return projectManagers.stream().findFirst();
    }
}
