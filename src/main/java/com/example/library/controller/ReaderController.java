package com.example.library.controller;

import com.example.library.dto.TicketDto;
import com.example.library.dto.View;
import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import com.example.library.service.TicketService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("reader")
@Api(tags = "Readers")
@AllArgsConstructor
public class ReaderController {
    private final ReaderService readerService;
    private final TicketService ticketService;

    @GetMapping("/getReaderInfo")
    @ApiOperation("Получение информации о читателе")
    public Reader getById() {
        return readerService.getById();
    }

    @PutMapping("/updateReader")
    @ApiOperation("Обновить информацию о читателе")
    public void updateReaderInformation(@RequestBody @Valid Reader reader) {
        readerService.update(reader);
    }

    @PostMapping("/getBook")
    @ApiOperation("Читатель берет книгу")
    public void getBook(@RequestParam long bookId) {
        ticketService.getBook(bookId);
    }

    @PostMapping("/returnBook")
    @ApiOperation("Читатель возвращает книгу")
    public void returnBook(@RequestParam long bookId) {
        ticketService.returnBook(bookId);
    }

    @GetMapping("/readerBooks")
    @JsonView(View.ReaderInfo.class)
    @ApiOperation("Просмотр нынешних или взятых ранее книг читателя")
    public List<TicketDto> getReaderBooks() {
        return ticketService.getReaderBooks();
    }
}