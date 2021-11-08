package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookInformation;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public BookInformation bookInformation() {
        List<BookDto> freeBooks = new ArrayList<>();
        List<BookDto> busyBooks = new ArrayList<>();

        List<Book> books = findAll();
        List<Long> booksIds = books.stream().map(Book::getId).collect(Collectors.toList());




        List<BookDto> dtoBooks = findAll().stream()
                .map(b -> new BookDto(b, countBusyBooks(b.getId())))
                .collect(Collectors.toList());

        for (BookDto dtoBook : dtoBooks) {
            if (dtoBook.getUsed() != 0) {
                busyBooks.add(dtoBook);
            } else {
                freeBooks.add(dtoBook);
            }
        }
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

        if (bookFromDB.isPresent() && book.getAmount() >= countBusyBooks(bookId)) {
            Book oldBook = bookFromDB.get();
            oldBook.setName(book.getName());
            oldBook.setAuthor(book.getAuthor());
            oldBook.setYear(book.getYear());
            oldBook.setAmount(book.getAmount());
            bookRepository.saveAndFlush(oldBook);
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
        return ticketRepository.findAll().stream()
                .filter(b -> b.getBookId().getId().equals(bookId) && b.getDateTo().equals(""))
                .count();
    }
}