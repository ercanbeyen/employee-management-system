CREATE TABLE payment (
    id INT AUTO_INCREMENT NOT NULL,
    employee_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    currency VARCHAR(50) NOT NULL,
    type VARCHAR(100) NOT NULL,
    latest_change_at DATETIME(6) NOT NULL,
    latest_change_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (employee_id)
        REFERENCES employee(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;