package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        Optional<Book> book = findAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        return book.orElseGet(Book::new);
    }

    public void update(Long id, Book book) {
        Optional<Book> bookFromDB = findAll().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (bookFromDB.isPresent()) {
            Book oldBook = bookFromDB.get();
            oldBook.setName(book.getName());
            oldBook.setAuthor(book.getAuthor());
            oldBook.setYear(book.getYear());
            oldBook.setBusy(book.getBusy());
            bookRepository.saveAndFlush(oldBook);
        }
    }

    public void delete(Long id) {
        findAll().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .ifPresent(bookRepository::delete);
    }
}