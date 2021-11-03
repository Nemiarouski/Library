package com.example.library.repository;

import com.example.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    @Query("select r from Reader r where r.id = :id")
    Reader findById(@Param("id") int id);
}