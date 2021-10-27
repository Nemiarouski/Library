package com.example.library.repository;

import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findByName(String name);
    List<Reader> findBySurname(String surname);
}