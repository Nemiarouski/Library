package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookInformation;
import com.example.library.exception.BadRequestException;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);
    private final BookRepository bookRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       TicketRepository ticketRepository) {
        this.bookRepository = bookRepository;
        this.ticketRepository = ticketRepository;
    }

    public void save(Book book) {
        if (bookRepository.findByName(book.getName()).isPresent()) {
            logger.info("This book already exists.");
            throw new BadRequestException("This book already exists.");
        }
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public BookInformation getBookInformation() {
        List<BookDto> freeBooks;
        List<BookDto> busyBooks;
        List<Book> books = findAll();

        Map<Long, Long> ticketMap = ticketRepository.findByBookInAndDateToIsNull(books).stream()
                .collect(Collectors.groupingBy(Ticket::getBook))
                .entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getId(), e -> (long) e.getValue().size()));

        List<BookDto> dtoBooks = books.stream()
                .map(b -> new BookDto(b, ticketMap.get(b.getId())))
                .collect(Collectors.toList());

        Map<Boolean, List<BookDto>> freeBusyBooks = dtoBooks.stream()
                .collect(Collectors.partitioningBy(s -> s.getUsed() != 0));

        freeBooks = new ArrayList<>(freeBusyBooks.get(false));
        busyBooks = new ArrayList<>(freeBusyBooks.get(true));

        return new BookInformation(freeBooks, busyBooks);
    }

    public BookDto getById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            Long count = countBusyBooks(book.get().getId());
            return new BookDto(book.get(), count);
        } else {
            logger.info("Book is not found");
            throw new NotFoundException();
        }
    }

    public void update(Long bookId, Book book) {
        Optional<Book> bookFromDB = bookRepository.findById(bookId);

        if (bookFromDB.isPresent()) {
            Long count = countBusyBooks(bookId);

            if (book.getAmount() >= count) {
                Book oldBook = bookFromDB.get();
                oldBook.copyOf(book, count);
                bookRepository.saveAndFlush(oldBook);
            } else {
                logger.info("Not enough books to update.");
                throw new NotFoundException();
            }
        } else {
            logger.info("Book is not exist.");
            throw new NotFoundException();
        }
    }

    public void delete(Long bookId) {
        if (countBusyBooks(bookId) == 0) {
            bookRepository.findById(bookId).ifPresent(bookRepository::delete);
        } else {
            logger.info("Book is not exist or busy.");
            throw new NotFoundException();
        }
    }

    private Long countBusyBooks(Long bookId) {
        return ticketRepository.countByBookIdAndDateToIsNull(bookId);
    }
}