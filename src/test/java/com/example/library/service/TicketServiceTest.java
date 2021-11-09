package com.example.library.service;

import com.example.library.dto.TicketDto;
import com.example.library.model.Book;
import com.example.library.model.Reader;
import com.example.library.model.Ticket;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReaderRepository;
import com.example.library.repository.TicketRepository;
import com.example.library.utils.TicketComparator;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class) JUnit 5
//@RunWith(MockitoJUnitRunner.class) JUnit 4 -->
//@SpringBootTest
class TicketServiceTest {
/*    @Mock
    TicketRepository ticketRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    ReaderRepository readerRepository;
    @InjectMocks
    TicketService ticketService;*/

    TicketRepository ticketRepository = mock(TicketRepository.class);
    BookRepository bookRepository = mock(BookRepository.class);
    ReaderRepository readerRepository = mock(ReaderRepository.class);

    TicketService ticketService = new TicketService(ticketRepository,
            bookRepository,
            readerRepository);

    @Test
    void getBook() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 6L);

        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ticketService.getBook(1L, 1L);
        assertEquals(5, book.getAmount());
    }

    @Test
    void returnBook() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Ticket ticket = new Ticket(reader, book, "2021-04-07 14:22", "");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(ticketRepository.findByReaderIdAndBookIdAndDateToIsNull(1L, 1L)).thenReturn(Optional.of(ticket));

        ticketService.returnBook(1L, 1L);
        assertEquals(8, book.getAmount());
    }

    @Test
    void getTickets() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Book book2 = new Book("Cloud Atlas", "David Mitchell", "2004", 3L);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(reader, book1, "2020-06-11 14:13", "2020-07-04 12:24"));
        tickets.add(new Ticket(reader, book2, "2021-01-02 11:10", ""));

        when(ticketRepository.findAll()).thenReturn(tickets);

        Map<String, List<TicketDto>> mapTickets = ticketService.getTickets();

        //Map<String, List<TicketDto>> expectedTreeMap = new TreeMap<>(new TicketComparator());

    }

    @Test
    void getReaderBooks() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        reader.setId(1L);
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Book book2 = new Book("Cloud Atlas", "David Mitchell", "2004", 3L);
        Ticket ticket1 = new Ticket(reader, book1, "2020-06-11 14:13", "2020-07-04 12:24");
        Ticket ticket2 = new Ticket(reader, book2, "2021-01-02 11:10", "");
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        when(ticketRepository.findAll()).thenReturn(tickets);

        assertEquals("Triumphal Arch", ticketService.getReaderBooks(1L).get(0).getBookName());
    }
}