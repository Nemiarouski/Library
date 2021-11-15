package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t from Ticket t join fetch t.reader r join fetch t.book b where r.id = :reader_id and b.id = :book_id and t.dateTo IS NULL")
    Optional<Ticket> findByReaderAndBookAndDateToIsNull(@Param("reader_id") Long readerId, @Param("book_id") Long bookId);
    List<Ticket> findByBookInAndDateToIsNull(List<Book> books);
    long countByBookIdAndDateToIsNull(Long bookId);
    List<Ticket> findByReaderId(Long readerId);
}