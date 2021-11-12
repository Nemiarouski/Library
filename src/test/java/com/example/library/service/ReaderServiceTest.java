package com.example.library.service;

import com.example.library.exception.NotFoundException;
import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void getById() {
        assertThrows(NotFoundException.class, () -> readerService.getById(1L));
    }

    @Test
    void update() {
        Reader oldReader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Reader newReader = new Reader("Robert", "Stinson", "admin", "111", true, "ROLE_READER");
        Reader expectedReader = new Reader("Robert", "Stinson", "admin", "111", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(oldReader));

        readerService.update(1L, newReader);

        assertEquals(expectedReader, newReader);
    }

    @Test
    void delete() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));

        readerService.delete(1L);

        verify(readerRepository, times(1)).delete(reader);
    }
}