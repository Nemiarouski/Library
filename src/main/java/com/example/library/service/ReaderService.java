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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReaderService {
    private static final Logger logger = LogManager.getLogger(ReaderService.class);
    private final ReaderRepository readerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.readerRepository = readerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(Reader reader) {
        reader.setPassword(bCryptPasswordEncoder.encode(reader.getPassword()));
        readerRepository.save(reader);
    }

    public List<Reader> findAll() {
        return readerRepository.findAll();
    }

    public Reader getById(Long readerId) {
        Optional<Reader> reader = readerRepository.findById(readerId);

        if (reader.isPresent()) {
            return reader.get();
        } else {
            logger.info("Reader is not exist.");
            throw  new NotFoundException();
        }
    }

    public void update(Long readerId, Reader reader) {
        Optional<Reader> readerFromDB = readerRepository.findById(readerId);

        if (readerFromDB.isPresent()) {
            Reader oldReader = readerFromDB.get();
            oldReader.copyOf(reader);
            readerRepository.saveAndFlush(oldReader);
        } else {
            logger.info("Reader is not exist.");
            throw  new NotFoundException();
        }
    }

    public void delete(Long readerId) {
        Optional<Reader> reader = readerRepository.findById(readerId);

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