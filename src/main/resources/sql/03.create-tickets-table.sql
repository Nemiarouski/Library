CREATE TABLE IF NOT EXISTS tickets (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    reader_id INT NOT NULL REFERENCES readers (id) ON DELETE CASCADE,
    books_id INT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    date_from DATE NOT NULL,
    date_to DATE,
    UNIQUE (reader_id, books_id)
)