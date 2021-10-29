package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
@Api
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
    @ApiOperation("Создание нового читателя")
    public void addReader(@RequestBody Reader reader) {
        readerService.save(reader);
    }

    @DeleteMapping("/readers/{id}")
    @ApiOperation("Удаление существующего читателя")
    public void deleteReader(@PathVariable Long id) {
        readerService.delete(id);
    }

    @GetMapping("/readers")
    @ApiOperation("Получение списка всех читателей")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }

    @PostMapping("/books/new")
    @ApiOperation("Создание новой книги")
    public void addBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @PutMapping("/books/{id}")
    @ApiOperation("Внесение изменений в существующую книгу")
    public void update(@PathVariable Long id, @RequestBody Book book) {
        bookService.update(id, book);
    }

    @DeleteMapping("/books/{id}")
    @ApiOperation("Удаление книги")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/books")
    @ApiOperation("Получение всех книг")
    public List<Book> allBooks() {
        return bookService.findAll();
    }

    @GetMapping("books/{id}")
    @ApiOperation("Получение одной книги")
    public Book getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/tickets")
    @ApiOperation("Получение билетов")
    public List<Ticket> getTickets() {
        return ticketService.findAll();
    }
}