package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void setTicketRepository(TicketRepository ticketRepository, BookRepository bookRepository,ReaderRepository readerRepository) {
        this.ticketRepository = ticketRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void getBook(Long id, Long bookId, String date) {
        Reader reader = readerRepository.getById(id);
        Book book = bookRepository.getById(bookId);

        book.setBusy(true);
        Ticket ticket = new Ticket(reader, book, date, "");

        ticketRepository.saveAndFlush(ticket);
    }

    public void returnBook(Long id, Long bookId, String date) {
        Book book = bookRepository.getById(bookId);
        book.setBusy(false);

        Optional<Ticket> ticket = findAll().stream()
                .filter(t -> t.getReaderId().getId().equals(id) & t.getBookId().getId().equals(bookId))
                .findFirst();
        if(ticket.isPresent()) {
            Ticket oldTicket = ticket.get();
            oldTicket.setDateTo(date);
            ticketRepository.saveAndFlush(oldTicket);
        }
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.getById(id);
    }

    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    public void update(Ticket ticket) {
        ticketRepository.saveAndFlush(ticket);
    }

    public List<String> getReaderBooks(Long id) {
        return findAll().stream()
                .filter(b -> b.getReaderId().getId().equals(id))
                .sorted(Comparator.comparing(Ticket::getDateFrom))
                .map(t -> "[Book]: " + t.getBookId().getName() + " [Date from]: " + t.getDateFrom() +  " [Date to]: " + t.getDateTo())
                .collect(Collectors.toList());
    }
}