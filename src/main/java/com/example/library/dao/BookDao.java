package com.example.library.dao;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookDao extends JpaRepository<Book, Long> {
    Book findByName(String name);
    List<Book> findByAuthor(String author);
    List<Book> findByYear(String year);
}