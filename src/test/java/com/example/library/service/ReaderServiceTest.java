package com.example.library.service;

import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReaderServiceTest {
    ReaderRepository readerRepository = mock(ReaderRepository.class);
    ReaderService readerService = new ReaderService(readerRepository);

    @Test
    void save() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.save(reader);

        verify(readerRepository, times(1)).save(reader);
    }

    @Test
    void  findAll() {
        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader("ReaderName1", "ReaderSurName1", "ReaderLogin1", "ReaderPassword1", true, "ROLE_READER"));
        readers.add(new Reader("ReaderName2", "ReaderSurName2", "ReaderLogin2", "ReaderPassword2", true, "ROLE_READER"));
        readers.add(new Reader("ReaderName3", "ReaderSurName3", "ReaderLogin3", "ReaderPassword3", true, "ROLE_READER"));
        readers.add(new Reader("ReaderName4", "ReaderSurName4", "ReaderLogin4", "ReaderPassword4", true, "ROLE_READER"));

        when(readerRepository.findAll()).thenReturn(readers);

        assertEquals(4, readerService.findAll().size());
    }

    @Test
    void getById() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        assertEquals("Michel", readerService.getById(1L).getName());
    }

    @Test
    void update() {
        Reader oldReader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Reader newReader = new Reader("Robert", "Stinson", "admin", "111", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(oldReader));

        readerService.update(1L, newReader);

        assertEquals("Robert", readerService.getById(1L).getName());
        assertEquals("Stinson", readerService.getById(1L).getSurname());
        assertEquals("admin", readerService.getById(1L).getLogin());
        assertEquals("111", readerService.getById(1L).getPassword());
    }

    @Test
    void delete() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.delete(1L);

        verify(readerRepository, times(1)).delete(reader);
    }

    @Test
    void findReaders() {
        //Pageable
    }
}