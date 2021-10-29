package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("readers")
@Api(tags = "Readers")
public class ReaderController {
    private final ReaderService readerService;
    private final BookService bookService;
    private final TicketService ticketService;

    @Autowired
    public ReaderController(ReaderService readerService, BookService bookService, TicketService ticketService) {
        this.readerService = readerService;
        this.bookService = bookService;
        this.ticketService = ticketService;
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию о читателе")
    public void update(@PathVariable Long id, @RequestBody Reader reader) {
        readerService.update(id, reader);
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение информации о читателе")
    public Reader getById(@PathVariable Long id) {
        return readerService.getById(id);
    }

    @PostMapping("/{id}")
    @ApiOperation("Читатель берет книгу")
    public void getBook(@PathVariable Long id,
                        @RequestBody Long bookId,
                        @RequestParam String date) {
        ticketService.getBook(id, bookId, date);
    }

    @PostMapping("/{id}/r")
    @ApiOperation("Читатель возвращает книгу")
    public void returnBook(@PathVariable Long id,
                           @RequestBody Long bookId,
                           @RequestParam String date) {
        ticketService.returnBook(id, bookId, date);
    }

    @GetMapping("/{id}/books")
    @ApiOperation("Просмотр нынешних или взятых ранее книг читателя")
    public List<String> getReaderBooks(@PathVariable Long id) {
        return ticketService.getReaderBooks(id);
    }
}