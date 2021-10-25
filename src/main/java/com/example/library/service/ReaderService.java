package com.example.library.service;

import com.example.library.dao.ReaderDao;
import com.example.library.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderService {
    private final ReaderDao readerDao;

    @Autowired
    public ReaderService(ReaderDao readerDao) {
        this.readerDao = readerDao;
    }

    public List<Reader> findAll() {
        return readerDao.findAll();
    }

    public List<Reader> findByName(String name) {
        return readerDao.findByName(name);
    }

    public List<Reader> findBySurname(String surname) {
        return readerDao.findByName(surname);
    }
}