package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TicketServiceTest {

    @Mock
    TicketRepository ticketRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    ReaderRepository readerRepository;
    @InjectMocks
    TicketService ticketService = new TicketService(ticketRepository, bookRepository, readerRepository);

    @Test
    void getBook() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 6L);

        when(readerRepository.findById(1L)).thenReturn(java.util.Optional.of(reader));
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(book));

        ticketService.getBook(1L, 1L);
        assertEquals(5, bookRepository.getById(1L).getAmount());
    }

    @Test
    void returnBook() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 5L);
        Book book2 = new Book("Arch", "Erich", "1999", 20L);
        List<Ticket> tickets = Arrays.asList(new Ticket(reader, book1, "2021-04-25", ""),
                                            new Ticket(reader, book2, "2021-04-25", ""));

        when(readerRepository.findById(2L)).thenReturn(java.util.Optional.of(reader));
        when(bookRepository.findById(2L)).thenReturn(java.util.Optional.of(book1));
        when(ticketRepository.findAll()).thenReturn(tickets);

        ticketService.getBook(2L, 2L);
        assertEquals(4,book1.getAmount());

        ticketService.returnBook(2L, 2L);
        assertEquals(5, book1.getAmount());

    }

    @Test
    void findAll() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 5L);
        Book book2 = new Book("Arch", "Erich", "1999", 20L);
        List<Ticket> tickets = Arrays.asList(new Ticket(reader, book1, "2021-04-25", ""),
                new Ticket(reader, book2, "2021-04-25", ""));

        when(ticketRepository.findAll()).thenReturn(tickets);
        assertEquals(2, ticketService.findAll().size());

    }
}