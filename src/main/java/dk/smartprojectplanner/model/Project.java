package dk.smartprojectplanner.model;

import java.time.LocalDate;


/**
 * A project owned by one project manager. Matches one row in the project table.
 */
public class Project {

    private int id;
    private int projectManagerId;
    private String title;
    private String description;
    private LocalDate deadline;
    private LocalDate startDate;
    private LocalDate endDate;
    private String shareToken;

    /**
     * Creates a project with all fields, used when reading a row from the database.
     *
     * @param id                the generated id from the database of the project
     * @param projectManagerId  the id of the project manager who owns the project
     * @param title             the title of the project (max 100 characters)
     * @param description       a short description of the project (max 250 characters) - may be null
     * @param deadline          the target date for completing the project
     * @param startDate         the date the project starts
     * @param endDate           the actual date the project finished - null until the project is completed
     * @param shareToken        the unique token used to share the project - null until sharing is set up
     */
    public Project(int id, int projectManagerId, String title, String description,
                   LocalDate deadline, LocalDate startDate, LocalDate endDate, String shareToken) {
        this.id = id;
        this.projectManagerId = projectManagerId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shareToken = shareToken;
    }

    /**
     * Creates an empty project, used by Thymeleaf when binding the create form.
     */
    public Project() {}

    //-----Getters-----

    public int getId() {
        return id;
    }

    public int getProjectManagerId() {
        return projectManagerId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getShareToken() {
        return shareToken;
    }


    //-----Setters-----

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectManagerId(int projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setShareToken(String shareToken) {
        this.shareToken = shareToken;
    }
}
