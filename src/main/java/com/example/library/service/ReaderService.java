package com.example.library.service;

import com.example.library.repository.ReaderRepository;
import com.example.library.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public void save(Reader reader) {
        readerRepository.save(reader);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

}