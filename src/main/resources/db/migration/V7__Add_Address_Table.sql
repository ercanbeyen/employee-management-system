CREATE TABLE address (
    id int AUTO_INCREMENT NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postCode VARCHAR(100) NOT NULL,
    latest_change_at DATETIME(6) NOT NULL,
    latest_change_by VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;