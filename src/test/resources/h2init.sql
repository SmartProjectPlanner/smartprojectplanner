DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS project_manager;



CREATE TABLE project_manager (
     id INT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(100) UNIQUE NOT NULL,
     password VARCHAR(100) NOT NULL
);

CREATE TABLE project (
     id INT AUTO_INCREMENT PRIMARY KEY,
     project_manager_id INT NOT NULL,
     title VARCHAR(100) NOT NULL,
     description VARCHAR(250),
     deadline DATE NOT NULL,
     start_date DATE NOT NULL,
     end_date DATE,
     share_token VARCHAR(100) UNIQUE,
     FOREIGN KEY (project_manager_id) REFERENCES project_manager(id)
);

INSERT INTO project_manager (id, name, email, password) VALUES (1, 'Test Manager', 'test@test.dk', 'test123');
INSERT INTO project_manager (id, name, email, password) VALUES (2, 'Anden Manager', 'anden@test.dk', 'test123');