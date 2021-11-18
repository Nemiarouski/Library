package com.example.library.service;

import com.example.library.dto.TicketDto;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TicketService {
    private static final Logger logger = LogManager.getLogger(TicketService.class);
    private final static DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final TicketRepository ticketRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final UserService userService;

    public void getBook(Long bookId) {
        Long readerId = userService.getUserId();
        Optional<Ticket> ticketFromDb = ticketRepository.findByReaderAndBookAndDateToIsNull(readerId, bookId);

        if (ticketFromDb.isPresent()) {
            logger.info("Ticket exists.");
            throw new NotFoundException();
        }

        Optional<Reader> reader = readerRepository.findById(readerId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (reader.isPresent() && book.isPresent() && book.get().getAmount() >= 1) {
            book.get().setAmount(book.get().getAmount() - 1);
            Ticket ticket = new Ticket(reader.get(), book.get(), getTime(), null);
            ticketRepository.saveAndFlush(ticket);
        } else {
            logger.info("Can't find reader or book");
            throw new NotFoundException();
        }
    }

    public void returnBook(Long bookId) {
        Long readerId = userService.getUserId();
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Reader> reader = readerRepository.findById(readerId);

        if (!reader.isPresent() || !book.isPresent()) {
            logger.info("Can't find reader or book");
            throw new NotFoundException();
        }

        Optional<Ticket> ticket = ticketRepository.findByReaderAndBookAndDateToIsNull(readerId, bookId);

        if (ticket.isPresent()) {
            Ticket newTicket = ticket.get();
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
                .collect(Collectors.groupingBy(TicketDto::getBookName, LinkedHashMap::new, Collectors.toList()));
    }

    public List<TicketDto> getReaderBooks() {
        Long readerId = userService.getUserId();
        return ticketRepository.findByReaderId(readerId).stream()
                .sorted(Comparator.comparing(Ticket::getDateFrom))
                .map(TicketDto::new)
                .collect(Collectors.toList());
    }

    private static LocalDateTime getTime() {
        return LocalDateTime.parse(LocalDateTime.now().format(PATTERN), PATTERN);
    }
}