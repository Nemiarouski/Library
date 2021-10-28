package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final ReaderService readerService;
    private final BookService bookService;
    private final TicketService ticketService;

    @Autowired
    public AdminController(ReaderService readerService, BookService bookService, TicketService ticketService) {
        this.readerService = readerService;
        this.bookService = bookService;
        this.ticketService = ticketService;
    }

    @PostMapping("/readers/new")
    public void addReader(@RequestBody Reader reader) {
        readerService.save(reader);
    }

    @DeleteMapping("/readers/{id}")
    public void deleteReader(@PathVariable Long id) {
        readerService.delete(id);
    }

    @GetMapping("/readers")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }

    @PostMapping("/books/new")
    public void addBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @PutMapping("/books/{id}")
    public void update(@PathVariable Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/books")
    public List<Book> allBooks() {
        return bookService.findAll();
    }

    @GetMapping("books/{id}")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }
}