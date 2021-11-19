package com.example.library.repository;

import com.example.library.model.Reader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    Optional<Reader> findByNameAndSurname(String name, String surname);
    Optional<Reader> findById(Long id);
    Page<Reader> findAll(Pageable pageable);
    Optional<Reader> findByLogin(String login);
}