CREATE DATABASE covid;

USE covid;

CREATE TABLE IF NOT EXISTS statistic (
    id INT AUTO_INCREMENT PRIMARY KEY,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_rep DATE,
    day TINYINT NOT NULL,
    month TINYINT NOT NULL,
    year INT NOT NULL,
    cases INT NOT NULL,
    deaths INT NOT NULL,
    country VARCHAR(255) NOT NULL,
    get_id VARCHAR(255) NOT NULL,
    country_code VARCHAR(255) NOT NULL,
    pop_data_2019 VARCHAR(255) NOT NULL,
    continent_exp VARCHAR(255) NOT NULL
)  ENGINE=INNODB;
