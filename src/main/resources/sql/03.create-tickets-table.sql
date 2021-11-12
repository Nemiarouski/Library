CREATE TABLE IF NOT EXISTS tickets (
    id INT NOT NULL PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    reader_id INT NOT NULL REFERENCES readers (id) ON DELETE CASCADE,
    book_id INT NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    date_from TIMESTAMP(0) NOT NULL,
    date_to TIMESTAMP(0)
)