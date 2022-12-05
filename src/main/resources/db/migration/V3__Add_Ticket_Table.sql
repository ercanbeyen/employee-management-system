CREATE TABLE ticket (
    id INT AUTO_INCREMENT NOT NULL,
    priority VARCHAR(50) NOT NULL,
    closed BOOLEAN NOT NULL,
    topic VARCHAR(50) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    latest_change_at DATETIME(6) NOT NULL,
    latest_change_by VARCHAR(255) NOT NULL,
    requester_id INT NOT NULL,
    assignee_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (requester_id)
        REFERENCES employee(id),
    FOREIGN KEY (assignee_id)
        REFERENCES employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;