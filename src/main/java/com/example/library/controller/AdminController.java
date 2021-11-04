package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookInformation;
import com.example.library.dto.TicketDto;
import com.example.library.dto.View;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
@Api(tags = "Admin")
public class AdminController {
    private final ReaderService readerService;
    private final BookService bookService;
    private final TicketService ticketService;

    @Autowired
    public AdminController(ReaderService readerService,
                           BookService bookService,
                           TicketService ticketService) {
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

    //Method to use Pageable.
    @GetMapping("/readers/pageable/{page}")
    public List<Reader> showReaders(@PathVariable int page,
                                    @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reader> readers = readerService.findReaders(pageable);
        return readers.getContent();
    }

    @PostMapping("/books/new")
    @ApiOperation("Создание новой книги")
    public void addBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @PutMapping("/books/{id}")
    @ApiOperation("Внесение изменений в существующую книгу")
    public void update(@PathVariable Long id,
                       @RequestBody Book book) {
        bookService.update(id, book);
    }

    @DeleteMapping("/books/{id}")
    @ApiOperation("Удаление книги")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }

    @GetMapping("/books")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение всех книг")
    public BookInformation allBooks() {
        return bookService.bookInformation();
    }

    @GetMapping("books/{id}")
    @ApiOperation("Получение одной книги")
    public BookDto getById(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/tickets")
    @ApiOperation("Получение билетов")
    public List<TicketDto> getTickets() {
        return ticketService.findAllTickets();
    }
}