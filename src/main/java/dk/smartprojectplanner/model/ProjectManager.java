package dk.smartprojectplanner.model;

public class ProjectManager extends Employee {

    private String password;

    public ProjectManager(int id, String name, String email, String password) {
        super(id, name, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

