package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.BookService;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final ReaderService readerService;
    private final BookService bookService;

    @Autowired
    public AdminController(ReaderService readerService, BookService bookService) {
        this.readerService = readerService;
        this.bookService = bookService;
    }

    @GetMapping("/readers")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        readerService.findAll().stream().filter(l -> l.getId().equals(id)).findFirst().ifPresent(readerService::delete);
    }

    @PostMapping("/new")
    public void createReader(@RequestBody String name, String surname, String login, String password, String role) {
        Reader reader = new Reader(name, surname, login, password, true, role);
        readerService.save(reader);
    }
}