package com.example.library.dao;

import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReaderDao extends JpaRepository<Reader, Long> {
    List<Reader> findByName(String name);
    List<Reader> findBySurname(String surname);
}