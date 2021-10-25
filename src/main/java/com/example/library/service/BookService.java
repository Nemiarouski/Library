package com.example.library.service;

import com.example.library.dao.BookDao;
import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookDao bookDao;

    @Autowired
    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public Book findByName(String name) {
        return bookDao.findByName(name);
    }

    public List<Book> findByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }

    public List<Book> findByYear(String year) {
        return bookDao.findByYear(year);
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }
}