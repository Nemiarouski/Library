package com.example.library.service;

import com.example.library.exception.NotFoundException;
import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class ReaderServiceTest {
    @Mock
    ReaderRepository readerRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserService userService;
    @InjectMocks
    ReaderService readerService;

    @Test
    void save() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");

        when(readerRepository.findByNameAndSurname("Michel", "Jackson")).thenReturn(Optional.empty());

        readerService.save(reader);

        verify(readerRepository, times(1)).save(reader);
    }

    @Test
    void getById() {
        when(userService.getUserId()).thenReturn(1L);

        assertThrows(NotFoundException.class, () -> readerService.getById());
    }

    @Test
    void update() {
        Reader oldReader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Reader newReader = new Reader("Robert", "Stinson", "admin", "111", true, "ROLE_READER");
        Reader expectedReader = new Reader("Robert", "Stinson", "admin", "111", true, "ROLE_READER");
        oldReader.setId(1L);
        newReader.setId(1L);
        expectedReader.setId(1L);

        when(userService.getUserId()).thenReturn(1L);
        when(readerRepository.findById(1L)).thenReturn(Optional.of(oldReader));

        readerService.update(newReader);

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