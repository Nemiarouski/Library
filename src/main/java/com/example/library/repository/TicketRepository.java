package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("select t from Ticket t join fetch t.reader r join fetch t.book b where r.id = :reader_id and b.id = :book_id and t.dateTo IS NULL")
    Ticket findByReaderAndBookAndDateToIsNull(@Param("reader_id") Long readerId, @Param("book_id") Long bookId);
    List<Ticket> findByBookIdInAndDateToIsNull(List<Book> books);
    long countByBookIdAndDateToIsNull(Long bookId);
}