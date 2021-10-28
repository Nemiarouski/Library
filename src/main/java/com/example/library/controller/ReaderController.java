package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("readers")
public class ReaderController {
    private final ReaderService readerService;
    private final BookService bookService;

    @Autowired
    public ReaderController(ReaderService readerService, BookService bookService) {
        this.readerService = readerService;
        this.bookService = bookService;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Reader reader) {
        readerService.update(id, reader);
    }
}