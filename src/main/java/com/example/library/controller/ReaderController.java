package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class ReaderController {
    private final BookService bookService;

    @Autowired
    public ReaderController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<Book> allBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable Long id) {
        Optional<Book> book = bookService.findAll().stream().filter(l -> l.getId().equals(id)).findFirst();
        return book.get();
    }
}