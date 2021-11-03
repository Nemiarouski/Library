package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TicketRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private static final Logger logger = LogManager.getLogger(BookService.class);
    private BookRepository bookRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository,
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

    public BookDto getById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Long count = ticketRepository.findAll().stream()
                    .filter(b -> b.getBookId().equals(book.get()) && b.getDateTo().equals(""))
                    .count();
            return new BookDto(book.get(), count);
        } else {
            logger.info("Book is not found");
            throw new NotFoundException();
        }
    }

    public void update(Long id, Book book) {
        Optional<Book> bookFromDB = bookRepository.findById(id);

        if (bookFromDB.isPresent() && book.getAmount() >= countBusyBooks(id)) {
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

    public void delete(Long id) {
        if (countBusyBooks(id) == 0) {
            bookRepository.findById(id).ifPresent(bookRepository::delete);
        } else {
            logger.info("Book is not exist.");
            throw new NotFoundException();
        }
    }

    private Long countBusyBooks(Long bookId) {
        return ticketRepository.findAll().stream()
                .filter(b -> b.getBookId().getId().equals(bookId) && b.getDateTo().equals(""))
                .count();
    }
}