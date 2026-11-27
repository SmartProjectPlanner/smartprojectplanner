DROP TABLE IF EXISTS subtask_associate;
DROP TABLE IF EXISTS task_assignment;
DROP TABLE IF EXISTS project_associate;
DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS subproject;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS associate;
DROP TABLE IF EXISTS project_manager;

CREATE TABLE project_manager(
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(100) NOT NULL,
                                email VARCHAR(100) UNIQUE NOT NULL,
                                password VARCHAR(100) NOT NULL
);

CREATE TABLE associate(
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          working_hours_per_day DECIMAL(4,2) NOT NULL
);

CREATE TABLE project(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        project_manager_id INT NOT NULL,
                        title VARCHAR(100) NOT NULL,
                        description VARCHAR(250),
                        deadline DATE NOT NULL,
                        start_date DATE NOT NULL,
                        end_date DATE,
                        share_token VARCHAR(100) UNIQUE,
                        FOREIGN KEY(project_manager_id) REFERENCES project_manager(id)
);

CREATE TABLE subproject(
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           project_id INT NOT NULL,
                           title VARCHAR(100) NOT NULL,
                           description VARCHAR(250),
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL,
                           FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE task(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     project_id INT NOT NULL,
                     subproject_id INT,
                     title VARCHAR(100) NOT NULL,
                     description VARCHAR(250),
                     time_estimate DECIMAL(7,2) NOT NULL,
                     start_date DATE NOT NULL,
                     done BOOLEAN NOT NULL DEFAULT FALSE,
                     FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE,
                     FOREIGN KEY(subproject_id) REFERENCES subproject(id) ON DELETE CASCADE
);

CREATE TABLE subtask(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        task_id INT NOT NULL,
                        title VARCHAR(100) NOT NULL,
                        description VARCHAR(250),
                        time_estimate DECIMAL(7,2) NOT NULL,
                        done BOOLEAN NOT NULL DEFAULT FALSE,
                        FOREIGN KEY(task_id) REFERENCES task(id) ON DELETE CASCADE
);

CREATE TABLE project_associate(
                                  project_id INT NOT NULL,
                                  associate_id INT NOT NULL,
                                  PRIMARY KEY(project_id, associate_id),
                                  FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE,
                                  FOREIGN KEY(associate_id) REFERENCES associate(id) ON DELETE CASCADE
);

CREATE TABLE task_assignment(
                                task_id INT NOT NULL,
                                associate_id INT NOT NULL,
                                planned_hours_per_day DECIMAL(4,2) NOT NULL,
                                PRIMARY KEY(task_id, associate_id),
                                FOREIGN KEY(task_id) REFERENCES task(id) ON DELETE CASCADE,
                                FOREIGN KEY(associate_id) REFERENCES associate(id) ON DELETE CASCADE
);

CREATE TABLE subtask_associate(
                                  subtask_id INT NOT NULL,
                                  associate_id INT NOT NULL,
                                  PRIMARY KEY(subtask_id, associate_id),
                                  FOREIGN KEY(subtask_id) REFERENCES subtask(id) ON DELETE CASCADE,
                                  FOREIGN KEY(associate_id) REFERENCES associate(id) ON DELETE CASCADE
);