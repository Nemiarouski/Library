package com.example.library.repository;

import com.example.library.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByReaderIdAndBookIdAndDateToIsNull(Long readerId, Long bookId);
    List<Ticket> findByBookIdInAndDateToIsNull(List<Long> ticketsIds);
}