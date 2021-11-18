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
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
@Api(tags = "Admin")
@AllArgsConstructor
public class AdminController {
    private final ReaderService readerService;
    private final BookService bookService;
    private final TicketService ticketService;

    @PostMapping("/newReader")
    @ApiOperation("Создание нового читателя")
    public void addReader(@RequestBody @Valid Reader reader) {
        readerService.save(reader);
    }

    @DeleteMapping("/deleteReader/{readerId}")
    @ApiOperation("Удаление существующего читателя")
    public void deleteReader(@PathVariable Long readerId) {
        readerService.delete(readerId);
    }

    @GetMapping("/allReaders")
    @ApiOperation("Получение списка всех читателей")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }

    @GetMapping("/allReadersPageable/{page}")
    @ApiOperation("Создание постраничного вывода")
    public List<Reader> showReaders(@PathVariable int page,
                                    @RequestParam int size) {
        return readerService.findReaders(page, size);
    }

    @PostMapping("/newBook")
    @ApiOperation("Создание новой книги")
    public void addBook(@RequestBody @Valid Book book) {
        bookService.save(book);
    }

    @PutMapping("/updateBook/{bookId}")
    @ApiOperation("Внесение изменений в существующую книгу")
    public void update(@PathVariable Long bookId,
                       @RequestBody @Valid Book book) {
        bookService.update(bookId, book);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    @ApiOperation("Удаление книги")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }

    @GetMapping("/allBooks")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение всех книг")
    public BookInformation allBooks() {
        return bookService.getBookInformation();
    }

    @GetMapping("/getBookInfo/{bookId}")
    @ApiOperation("Получение одной книги")
    public BookDto getById(@PathVariable Long bookId) {
        return bookService.getById(bookId);
    }

    @GetMapping("/allTickets")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение билетов")
    public Map<String, List<TicketDto>> getTickets() {
        return ticketService.getTickets();
    }
}