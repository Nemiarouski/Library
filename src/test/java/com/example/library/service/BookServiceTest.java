package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookInformation;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TicketRepository;
import com.example.library.utils.ConfigProperties;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class BookServiceTest {
    @Mock
    BookRepository bookRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    ConfigProperties configProperties;
    @InjectMocks
    BookService bookService;

    @Test
    void save() {
        Book book = new Book("Book1", "Author1", "1990", 5L);

        when(bookRepository.findByName("Book1")).thenReturn(Optional.empty());
        when(configProperties.getAmount()).thenReturn(7);

        bookService.save(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void bookInformation() {
        Book book1 = new Book("Book1", "Author1", "1990", 5L);
        Book book2 = new Book("Book2", "Author2", "1993", 2L);
        Book book3 = new Book("Book3", "Author1", "1996", 10L);
        book1.setId(0L);
        book2.setId(1L);
        book3.setId(2L);
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Ticket ticket = new Ticket(reader, book1, LocalDateTime.of(2020, 7,15, 11,33), null);

        List<Book> books = new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();

        books.add(book1);
        books.add(book2);
        books.add(book3);
        tickets.add(ticket);

        Book expectedBook = new Book("Book1", "Author1", "1990", 5L);
        expectedBook.setId(0L);
        BookDto expectedBookDto = new BookDto(expectedBook, 1L);
        List<BookDto> expectedList = new ArrayList<>();
        expectedList.add(expectedBookDto);

        when(bookRepository.findAll()).thenReturn(books);
        when(ticketRepository.findByBookInAndDateToIsNull(books)).thenReturn(tickets);

        BookInformation bookInformation = bookService.getBookInformation();

        assertEquals(expectedList, bookInformation.getBusyBooks());
    }

    @Test
    void getById() {
        assertThrows(NotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    void update() {
        Book oldBook = new Book("Book1", "Author1", "1990", 5L);
        Book newBook = new Book("Cloud Atlas", "David Mitchell", "2004", 7L);
        Book expectedBook = new Book("Cloud Atlas", "David Mitchell", "2004", 7L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(oldBook));
        when(configProperties.getAmount()).thenReturn(7);

        bookService.update(1L, newBook);

        assertEquals(expectedBook, newBook);
    }

    @Test
    void delete() {
        Book book = new Book("Book1", "Author1", "1990", 5L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.delete(1L);

        verify(bookRepository, times(1)).delete(book);
    }
}