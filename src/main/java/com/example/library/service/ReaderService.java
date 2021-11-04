package com.example.library.service;

import com.example.library.exception.NotFoundException;
import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private static final Logger logger = LogManager.getLogger(ReaderService.class);
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
        Optional<Reader> reader = readerRepository.findById(id);

        if (reader.isPresent()) {
            return reader.get();
        } else {
            logger.info("Reader is not exist.");
            throw  new NotFoundException();
        }
    }

    public void update(Long id, Reader reader) {
        Optional<Reader> readerFromDB = readerRepository.findById(id);

        if (readerFromDB.isPresent()) {
            Reader oldReader = readerFromDB.get();
            oldReader.setName(reader.getName());
            oldReader.setSurname(reader.getSurname());
            oldReader.setLogin(reader.getLogin());
            oldReader.setPassword(reader.getPassword());
            readerRepository.saveAndFlush(oldReader);
        } else {
            logger.info("Reader is not exist.");
            throw  new NotFoundException();
        }
    }

    public void delete(Long id) {
        Optional<Reader> reader = readerRepository.findById(id);

        if(reader.isPresent()) {
            readerRepository.delete(reader.get());
        } else {
            logger.info("Reader is not exist.");
            throw  new NotFoundException();
        }
    }

    public List<Reader> findReaders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reader> readers = readerRepository.findAll(pageable);
        return readers.getContent();
    }
}