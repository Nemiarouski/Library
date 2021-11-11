package com.example.library.service;

import com.example.library.dto.TicketDto;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private static final Logger logger = LogManager.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final static DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public TicketService(TicketRepository ticketRepository,
                         BookRepository bookRepository,
                         ReaderRepository readerRepository) {
        this.ticketRepository = ticketRepository;
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
    }

    public void getBook(Long readerId, Long bookId) {
        Optional<Reader> reader = readerRepository.findById(readerId);
        Optional<Book> book = bookRepository.findById(bookId);

        Ticket ticketFromDb = ticketRepository.findByReaderAndBookAndDateToIsNull(readerId, bookId);

        if (ticketFromDb != null) {
            logger.info("Ticket exists.");
            throw new NotFoundException();
        }

        if (reader.isPresent() && book.isPresent() && book.get().getAmount() >= 1) {
            book.get().setAmount(book.get().getAmount() - 1);
            Ticket ticket = new Ticket(reader.get(), book.get(), getTime(), null);
            ticketRepository.saveAndFlush(ticket);
        } else {
            logger.info("Can't find reader or book");
            throw new NotFoundException();
        }
    }

    public void returnBook(Long readerId, Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Reader> reader = readerRepository.findById(readerId);

        if(!reader.isPresent() || !book.isPresent()) {
            logger.info("Can't find reader or book");
            throw new NotFoundException();
        }

        Ticket ticket = ticketRepository.findByReaderAndBookAndDateToIsNull(readerId, bookId);

        if(ticket != null) {
            Ticket newTicket = ticket;
            book.get().setAmount(book.get().getAmount() + 1);
            newTicket.setDateTo(getTime());
            ticketRepository.saveAndFlush(newTicket);
        } else {
            logger.info("Ticket is not exist.");
            throw new NotFoundException();
        }
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Map<String, List<TicketDto>> getTickets() {
        return findAll().stream()
                .sorted(Comparator.comparing(Ticket::getBook)
                        .thenComparing(Ticket::getDateFrom)
                        .thenComparing(Ticket::getDateTo))
                .map(TicketDto::new)
                .collect(Collectors.groupingBy(TicketDto::getBookName));
    }

    public List<TicketDto> getReaderBooks(Long id) {
        return findAll().stream()
                .filter(b -> b.getReader().getId().equals(id))
                .sorted(Comparator.comparing(Ticket::getDateFrom))
                .map(TicketDto::new)
                .collect(Collectors.toList());
    }

    private static LocalDateTime getTime() {
        return LocalDateTime.parse(LocalDateTime.now().format(PATTERN), PATTERN);
    }
}