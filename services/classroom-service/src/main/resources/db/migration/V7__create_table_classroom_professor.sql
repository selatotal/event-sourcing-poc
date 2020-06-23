create table classroom_professor (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    classroom_id VARCHAR(50) NOT NULL,
    professor_id VARCHAR(50) NOT NULL
);