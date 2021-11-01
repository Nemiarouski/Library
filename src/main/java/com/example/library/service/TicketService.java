package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private BookRepository bookRepository;
    private ReaderRepository readerRepository;

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository,
                                    BookRepository bookRepository,
                                    ReaderRepository readerRepository) {
        this.ticketRepository = ticketRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void getBook(Long id, Long bookId) {
        Reader reader = readerRepository.getById(id);
        Book book = bookRepository.getById(bookId);

        if (book.getAmount() >= 1) {
            book.setAmount(book.getAmount() - 1);
            Ticket ticket = new Ticket(reader, book, getTime(), "");
            ticketRepository.saveAndFlush(ticket);
        } else {
            //In this moment program throws exception.
        }
    }

    public void returnBook(Long id, Long bookId) {
        Book book = bookRepository.getById(bookId);
        Optional<Ticket> ticket = findAll().stream()
                .filter(t -> t.getReaderId().getId().equals(id) & t.getBookId().getId().equals(bookId))
                .findFirst();

        if(ticket.isPresent() && ticket.get().getDateTo().equals("")) {
            book.setAmount(book.getAmount() + 1);
            Ticket oldTicket = ticket.get();
            oldTicket.setDateTo(getTime());
            ticketRepository.saveAndFlush(oldTicket);
        }
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public List<String> findAllTickets() {
        return findAll().stream()
                .sorted(Comparator.comparing(Ticket::getBookId)
                        .thenComparing(Ticket::getDateFrom)
                        .thenComparing(Ticket::getDateTo))
                .map(t -> "[Ticket]: #" + t.getId()
                        + " [Book]: " + t.getBookId().getName()
                        + " [Reader]: " + t.getReaderId().getName()
                        + " " + t.getReaderId().getSurname()
                        + " [Date from]: " + t.getDateFrom()
                        +  " [Date to]: " + t.getDateTo())
                .collect(Collectors.toList());
    }

    public List<String> getReaderBooks(Long id) {
        return findAll().stream()
                .filter(b -> b.getReaderId().getId().equals(id))
                .sorted(Comparator.comparing(Ticket::getDateFrom))
                .map(t -> "[Book]: " + t.getBookId().getName()
                        + " [Date from]: " + t.getDateFrom()
                        +  " [Date to]: " + t.getDateTo())
                .collect(Collectors.toList());
    }

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}