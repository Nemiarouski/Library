package com.example.library.controller;

import com.example.library.dto.TicketDto;
import com.example.library.dto.View;
import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("readers")
@Api(tags = "Readers")
public class ReaderController {
    private final ReaderService readerService;
    private final TicketService ticketService;

    @Autowired
    public ReaderController(ReaderService readerService,
                            TicketService ticketService) {
        this.readerService = readerService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{readerId}")
    @ApiOperation("Получение информации о читателе")
    public Reader getById(@PathVariable Long readerId) {
        return readerService.getById(readerId);
    }

    @PutMapping("/{readerId}")
    @ApiOperation("Обновить информацию о читателе")
    public void updateReaderInformation(@PathVariable Long readerId,
                                        @RequestBody @Valid Reader reader) {
        readerService.update(readerId, reader);
    }

    @PostMapping("/{readerId}")
    @ApiOperation("Читатель берет книгу")
    public void getBook(@PathVariable long readerId,
                        @RequestParam long bookId) {
        ticketService.getBook(readerId, bookId);
    }

    @PostMapping("/{readerId}/return")
    @ApiOperation("Читатель возвращает книгу")
    public void returnBook(@PathVariable long readerId,
                           @RequestParam long bookId) {
        ticketService.returnBook(readerId, bookId);
    }

    @GetMapping("/{readerId}/books")
    @JsonView(View.ReaderInfo.class)
    @ApiOperation("Просмотр нынешних или взятых ранее книг читателя")
    public List<TicketDto> getReaderBooks(@PathVariable Long readerId) {
        return ticketService.getReaderBooks(readerId);
    }
}