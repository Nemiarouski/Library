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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/reader/new")
    @ApiOperation("Создание нового читателя")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addReader(@RequestBody @Valid Reader reader) {
        readerService.save(reader);
    }

    @DeleteMapping("/reader/delete/{readerId}")
    @ApiOperation("Удаление существующего читателя")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteReader(@PathVariable Long readerId) {
        readerService.delete(readerId);
    }

    @GetMapping("/reader/all")
    @ApiOperation("Получение списка всех читателей")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }

    @GetMapping("/reader/all/pageable/{page}")
    @ApiOperation("Создание постраничного вывода")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Reader> showReaders(@PathVariable int page,
                                    @RequestParam int size) {
        return readerService.findReaders(page, size);
    }

    @PostMapping("/book/new")
    @ApiOperation("Создание новой книги")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addBook(@RequestBody @Valid Book book) {
        bookService.save(book);
    }

    @PutMapping("/book/update/{bookId}")
    @ApiOperation("Внесение изменений в существующую книгу")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(@PathVariable Long bookId,
                       @RequestBody @Valid Book book) {
        bookService.update(bookId, book);
    }

    @DeleteMapping("/book/delete/{bookId}")
    @ApiOperation("Удаление книги")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.delete(bookId);
    }

    @GetMapping("/book/all")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение всех книг")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookInformation allBooks() {
        return bookService.getBookInformation();
    }

    @GetMapping("/book/getInfo/{bookId}")
    @ApiOperation("Получение одной книги")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BookDto getById(@PathVariable Long bookId) {
        return bookService.getById(bookId);
    }

    @GetMapping("/ticket/all")
    @JsonView(View.AdminInfo.class)
    @ApiOperation("Получение билетов")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Map<String, List<TicketDto>> getTickets() {
        return ticketService.getTickets();
    }
}