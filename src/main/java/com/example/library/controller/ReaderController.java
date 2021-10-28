package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

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
        Optional<Reader> readerFromDB = readerService.findAll().stream().filter(r -> r.getId().equals(id)).findFirst();
        if (readerFromDB.isPresent()) {
            Reader newReader = readerFromDB.get();
            newReader.setName(reader.getName());
            newReader.setSurname(reader.getSurname());
            newReader.setLogin(reader.getLogin());
            newReader.setPassword(reader.getPassword());
            readerService.update(newReader);
        }
    }
}