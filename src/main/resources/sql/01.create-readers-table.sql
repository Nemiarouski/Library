CREATE TABLE IF NOT EXISTS readers (
    id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    enable BOOLEAN NOT NULL,
    authority VARCHAR(100) NOT NULL,
    UNIQUE (name, surname)
)