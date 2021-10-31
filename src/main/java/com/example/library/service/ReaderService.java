package com.example.library.service;

import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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
        Optional<Reader> reader = findAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();

        return reader.orElseGet(Reader::new);
    }

    public void update(Long id, Reader reader) {
        Optional<Reader> readerFromDB = findAll().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();

        if (readerFromDB.isPresent()) {
            Reader oldReader = readerFromDB.get();
            oldReader.setName(reader.getName());
            oldReader.setSurname(reader.getSurname());
            oldReader.setLogin(reader.getLogin());
            oldReader.setPassword(reader.getPassword());
            readerRepository.saveAndFlush(oldReader);
        }
    }

    public void delete(Long id) {
        findAll().stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .ifPresent(readerRepository::delete);
    }
}