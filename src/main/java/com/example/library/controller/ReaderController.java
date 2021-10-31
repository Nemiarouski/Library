package com.example.library.controller;

import com.example.library.model.Reader;
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
    private final TicketService ticketService;

    @Autowired
    public ReaderController(ReaderService readerService, TicketService ticketService) {
        this.readerService = readerService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Получение информации о читателе")
    public Reader getById(@PathVariable Long id) {
        return readerService.getById(id);
    }

    @PutMapping("/{id}")
    @ApiOperation("Обновить информацию о читателе")
    public void updateReaderInformation(@PathVariable Long id, @RequestBody Reader reader) {
        readerService.update(id, reader);
    }

    @PostMapping("/{id}")
    @ApiOperation("Читатель берет книгу")
    public void getBook(@PathVariable Long id,
                        @RequestBody Long bookId) {
        ticketService.getBook(id, bookId);
    }

    @PostMapping("/{id}/return")
    @ApiOperation("Читатель возвращает книгу")
    public void returnBook(@PathVariable Long id,
                           @RequestBody Long bookId) {
        ticketService.returnBook(id, bookId);
    }

    @GetMapping("/{id}/books")
    @ApiOperation("Просмотр нынешних или взятых ранее книг читателя")
    public List<String> getReaderBooks(@PathVariable Long id) {
        return ticketService.getReaderBooks(id);
    }
}