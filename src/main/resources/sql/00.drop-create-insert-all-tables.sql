DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS readers;

CREATE TABLE IF NOT EXISTS readers (
id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
name VARCHAR(100) NOT NULL,
surname VARCHAR(100) NOT NULL,
login VARCHAR(100) NOT NULL,
password VARCHAR(100) NOT NULL,
enable BOOLEAN NOT NULL,
authority VARCHAR(100) NOT NULL,
UNIQUE (name, surname)
);

CREATE TABLE IF NOT EXISTS books (
id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
name VARCHAR(100) NOT NULL,
author VARCHAR(100) NOT NULL,
year VARCHAR(4) NOT NULL,
busy BOOLEAN NOT NULL,
UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS tickets (
id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
reader_id INT NOT NULL REFERENCES readers (id) ON DELETE CASCADE,
books_id INT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
date_from DATE NOT NULL,
date_to DATE,
UNIQUE (reader_id, books_id)
);

INSERT INTO readers (name, surname, login, password, enable, authority) VALUES
('Bruce', 'Willis', 'diehard', '123', 'true', 'ROLE_READER'),
('Michel', 'Jackson', 'king', '777', 'true', 'ROLE_READER'),
('Jeff', 'Bezos', 'rocketman', '2021', 'true', 'ROLE_ADMIN');

INSERT INTO books (name, author, year, busy) VALUES
('Cloud Atlas', 'David Mitchell', '2004', 'false'),
('Erich Maria Remarque', 'Triumphal Arch', '1945', 'false'),
('Jack London', 'Martin Eden', '1909', 'false');

INSERT INTO tickets (reader_id, books_id, date_from, date_to) VALUES
(1, 2, '2021-03-24', '2021-04-01'),
(2, 1, '2021-03-27', '2021-04-14'),
(2, 2, '2021-05-01', '2021-05-07'),
(3, 3, '2021-07-11', '2021-07-19');