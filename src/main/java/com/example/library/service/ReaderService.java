package com.example.library.service;

import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
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

    public void save(Reader reader) {
        readerRepository.save(reader);
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader getById(Long id) {
        return readerRepository.getById(id);
    }

    public void delete(Reader reader) {
        readerRepository.delete(reader);
    }

    public void update(Reader reader) {
        readerRepository.saveAndFlush(reader);
    }
}