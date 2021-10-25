package com.example.library.controller;

import com.example.library.model.Reader;
import com.example.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping("/readers")
    public List<Reader> allReaders() {
        return readerService.findAll();
    }
}