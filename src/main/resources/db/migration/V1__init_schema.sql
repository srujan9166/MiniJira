CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    avatar_url TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE projects(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255),
	project_key VARCHAR(20) UNIQUE,
	description TEXT,
	user_id INT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE sprints(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	goal VARCHAR(255),
	start_date TIMESTAMP,
	end_date TIMESTAMP,
	status VARCHAR(20) CHECK (status IN ('PLANNED','ACTIVE','COMPLETED')),
	project_id INT NOT NULL,
	FOREIGN KEY(project_id) REFERENCES projects(id)
);

CREATE TABLE project_members(
	id SERIAL PRIMARY KEY,
	project_id INT,
	user_id INT,
	role VARCHAR(20),
	FOREIGN KEY(project_id) REFERENCES projects(id),
	FOREIGN KEY(user_id) REFERENCES users(id),
	UNIQUE(project_id,user_id)
);

CREATE TABLE issues(
	id SERIAL PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	description TEXT,
	type VARCHAR(50),
	priority VARCHAR(20),
	status VARCHAR(20),
	assignee_id INT,
	reporter_id INT,
	project_id INT NOT NULL,
	sprint_id INT,
	story_points INT,
	due_date DATE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	FOREIGN KEY(assignee_id) REFERENCES users(id),
	FOREIGN KEY(reporter_id) REFERENCES users(id),
	FOREIGN KEY(project_id) REFERENCES projects(id),
	FOREIGN KEY(sprint_id) REFERENCES sprints(id)
);

CREATE TABLE comments(
	id SERIAL PRIMARY KEY,
	content TEXT,
	author_id INT,
	issue_id INT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(author_id) REFERENCES users(id),
	FOREIGN KEY(issue_id) REFERENCES issues(id)
);

CREATE TABLE attachments(
	id SERIAL PRIMARY KEY,
	file_name VARCHAR(255) NOT NULL,
	file_path VARCHAR(500) NOT NULL,
	issue_id INT NOT NULL,
	uploaded_by INT NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(issue_id) REFERENCES issues(id),
	FOREIGN KEY(uploaded_by) REFERENCES users(id)
);

CREATE TABLE issue_links(
	id SERIAL PRIMARY KEY,
	source_issue_id INT NOT NULL,
	target_issue_id INT NOT NULL,
	link_type VARCHAR(20) CHECK (link_type IN ('BLOCKS','BLOCKED_BY','RELATES_TO','DUPLICATES')),
	FOREIGN KEY(source_issue_id) REFERENCES issues(id),
	FOREIGN KEY(target_issue_id) REFERENCES issues(id)
);

CREATE TABLE labels(
	id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	color VARCHAR(50),
	project_id INT,
	FOREIGN KEY(project_id) REFERENCES projects(id)
);

CREATE TABLE issue_labels(
	issue_id INT,
	label_id INT,
	PRIMARY KEY(issue_id, label_id),
	FOREIGN KEY(issue_id) REFERENCES issues(id),
	FOREIGN KEY(label_id) REFERENCES labels(id)
);

CREATE TABLE notifications(
	id SERIAL PRIMARY KEY,
	user_id INT NOT NULL,
	message TEXT NOT NULL,
	is_read BOOLEAN DEFAULT FALSE,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY(user_id) REFERENCES users(id)
);