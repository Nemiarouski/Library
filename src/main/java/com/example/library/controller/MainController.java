package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class MainController {
    private ReaderService readerService;

    @Autowired
    public MainController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, everyone!";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello, user!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello";
    }

    @GetMapping("/")
    public String main() {
        return "Main page.";
    }
}