CREATE TABLE comment (
    id int AUTO_INCREMENT NOT NULL,
    ticket_id INT NOT NULL,
    text VARCHAR(255),
    latest_change_at DATETIME(6) NOT NULL,
    latest_change_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(ticket_id)
        REFERENCES ticket(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;