package com.example.library.controller;

import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private ReaderService readerService;

    @Autowired
    public MainController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/user")
    public String user() {
        return "User";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }
}