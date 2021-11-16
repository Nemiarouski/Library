package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookInformation;
import com.example.library.dto.TicketDto;
import com.example.library.dto.View;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
@Api(tags = "Admin")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);
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

    @DeleteMapping("/readers/{readerId}")
    @ApiOperation("Удаление существующего читателя")
    public void deleteReader(@PathVariable Long readerId) {
        readerService.delete(readerId);
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
    public void addBook(@RequestBody @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("New book has invalid values in fields");
            throw new NotFoundException();
        }
        bookService.save(book);
    }

    @PutMapping("/books/{bookId}")
    @ApiOperation("Внесение изменений в существующую книгу")
    public void update(@PathVariable Long bookId,
                       @RequestBody @Valid Book book,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.warn("Book to update has invalid values in fields");
            throw new NotFoundException();
        }
        bookService.update(bookId, book);
    }

    @DeleteMapping("/books/{bookId}")
    @ApiOperation("Удаление книги")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }

    @GetMapping("/books")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение всех книг")
    public BookInformation allBooks() {
        return bookService.getBookInformation();
    }

    @GetMapping("books/{bookId}")
    @ApiOperation("Получение одной книги")
    public BookDto getById(@PathVariable Long bookId) {
        return bookService.getById(bookId);
    }

    @GetMapping("/tickets")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение билетов")
    public Map<String, List<TicketDto>> getTickets() {
        return ticketService.getTickets();
    }
}