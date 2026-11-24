package dk.smartprojectplanner.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectManagerRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectManagerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save() {

    }


}
