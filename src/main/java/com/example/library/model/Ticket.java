package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Reader can't be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @NotEmpty(message = "Book can't be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotEmpty(message = "Date from can't be empty.")
    @Column(name = "date_from")
    private LocalDateTime dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_to")
    private LocalDateTime dateTo;

    public Ticket(Reader reader, Book book, LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.reader = reader;
        this.book = book;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}