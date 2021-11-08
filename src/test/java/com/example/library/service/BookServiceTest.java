package com.example.library.service;

import com.example.library.dto.BookInformation;
import com.example.library.exception.NotFoundException;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BookServiceTest {
    BookRepository bookRepository = mock(BookRepository.class);
    TicketRepository ticketRepository = mock(TicketRepository.class);
    BookService bookService = new BookService(bookRepository, ticketRepository);

    @Test
    void save() {
        Book book = new Book("Book1", "Author1", "1990", 5L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.save(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void bookInformation() {
        List<Book> books = Arrays.asList(new Book("Book1", "Author1", "1990", 5L),
                                    new Book("Book2", "Author2", "1993", 2L),
                                    new Book("Book3", "Author1", "1996", 10L));
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        List<Ticket> tickets = Arrays.asList(new Ticket(reader, books.get(0), "2021-04-20 14:44", ""));

        when(bookRepository.findAll()).thenReturn(books);
        when(ticketRepository.findAll()).thenReturn(tickets);

        BookInformation bookInformation = bookService.bookInformation();

        assertEquals("Book1", bookInformation.getBusyBooks().get(0).getName());
    }

    @Test
    void getById() {
        assertThrows(NotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    void update() {
        Book oldBook = new Book("Book1", "Author1", "1990", 5L);
        Book newBook = new Book("Cloud Atlas", "David Mitchell", "2004", 20L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(oldBook));

        bookService.update(1L, newBook);

        Book expectedBook = new Book("Cloud Atlas", "David Mitchell", "2004", 20L);

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