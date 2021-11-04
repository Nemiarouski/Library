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
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/readers/pageable/{page}")
    @ApiOperation("Создание постраничного вывода")
    public List<Reader> showReaders(@PathVariable int page,
                                    @RequestParam int size) {
        return readerService.findReaders(page, size);
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
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение билетов")
    public Map<String, List<TicketDto>> getTickets() {
        return ticketService.getTickets();
    }
}