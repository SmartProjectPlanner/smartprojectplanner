package dk.smartprojectplanner.repository;

import dk.smartprojectplanner.model.ProjectManager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Selects all emails from database, loops through and checks if they match the new email a user
     * is trying to create.
     *
     * @param projectManager object variable from service class
     * @return false if the email does not exist
     * @throws IllegalArgumentException thrown if emails match
     */
    public boolean emailExists(ProjectManager projectManager) {
        String sql = """
                SELECT email 
                FROM project_manager                  
                """;
        List<String> emails = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("email"));

        for(String existingEmail : emails) {
            if(projectManager.getEmail().equals(existingEmail)) {
                throw new IllegalArgumentException("Email already exists");
            }
        }
        return false;
    }

    /**
     * saves a project manager in the database and validates using emailExists method
     *
     * @param projectManager object variable from service class
     * @param hashedPassword hashed password from service class
     * @return number of rows affected, a 1 if inserted successfully, 0 if it was unsuccessful
     */
    public int save(ProjectManager projectManager, String hashedPassword) {

        emailExists(projectManager);

        String sql = """
               INSERT INTO project_manager (name, email, password)
               VALUES(?,?,?)      
                """;

        return jdbcTemplate.update(sql, projectManager.getName(), projectManager.getEmail(), hashedPassword);
    }

    /**
     * Finds a project manager in database with id
     *
     * @param id project manager id from service class
     * @return project manager object with selected id
     * @return null if exception happens
     */
    public ProjectManager findById(int id) {

        String sql = """
                SELECT *
                FROM project_manager
                WHERE id = ?;
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProjectManager.class), id);
        } catch(Exception e) {
            return null;
        }

    }


}
