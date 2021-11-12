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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(ticketRepository.findByReaderAndBookAndDateToIsNull(1L, 1L)).thenReturn(Optional.empty());

        ticketService.getBook(1L, 1L);

        assertEquals(5, book.getAmount());
    }

    @Test
    void returnBook() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Ticket ticket = new Ticket(reader, book, LocalDateTime.of(2020, 6,11, 14,13), null);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(ticketRepository.findByReaderAndBookAndDateToIsNull(1L, 1L)).thenReturn(Optional.of(ticket));

        ticketService.returnBook(1L, 1L);

        assertEquals(8, book.getAmount());
    }

    @Test
    void getTickets() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Book book2 = new Book("Cloud Atlas", "David Mitchell", "2004", 3L);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(reader, book1, LocalDateTime.of(2020, 6,11, 14,13), LocalDateTime.of(2020, 7,4, 12,24)));
        tickets.add(new Ticket(reader, book2, LocalDateTime.of(2020, 7,15, 11,33), LocalDateTime.of(2020, 7,28, 10,15)));
        Map<String, List<TicketDto>> expectedMap = tickets.stream()
                .sorted(new TicketComparator())
                .map(TicketDto::new)
                .collect(Collectors.groupingBy(TicketDto::getBookName, Collectors.toList()));

        when(ticketRepository.findAll()).thenReturn(tickets);

        Map<String, List<TicketDto>> mapTickets = ticketService.getTickets();

        assertEquals(expectedMap, mapTickets);
    }

    @Test
    void getReaderBooks() {
        Reader reader = new Reader("Michel", "Jackson", "king", "777", true, "ROLE_READER");
        reader.setId(1L);
        Book book1 = new Book("Triumphal Arch", "Erich Maria Remarque", "1945", 7L);
        Book book2 = new Book("Cloud Atlas", "David Mitchell", "2004", 3L);
        Ticket ticket1 = new Ticket(reader, book1, LocalDateTime.of(2020, 6,11, 14,13), LocalDateTime.of(2020, 7,4, 12,24));
        Ticket ticket2 = new Ticket(reader, book2, LocalDateTime.of(2020, 7,15, 11,33), null);
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);

        when(ticketRepository.findAll()).thenReturn(tickets);

        assertEquals("Triumphal Arch", ticketService.getReaderBooks(1L).get(0).getBookName());
    }
}