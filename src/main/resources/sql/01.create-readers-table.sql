CREATE TABLE IF NOT EXISTS readers (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(40) NOT NULL,
    surname VARCHAR(40) NOT NULL,
    login VARCHAR(40) NOT NULL,
    password VARCHAR(40) NOT NULL,
    role_type VARCHAR(40) NOT NULL,
    UNIQUE (name, surname)
)