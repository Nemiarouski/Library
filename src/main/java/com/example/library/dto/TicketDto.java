package com.example.library.dto;

import com.example.library.model.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TicketDto {
    private Long id;

    @JsonView(View.ReaderInfo.class)
    private String bookName;

    @JsonView(View.AdminInfo.class)
    private String readerName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView({View.ReaderInfo.class, View.AdminInfo.class})
    private LocalDateTime dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView({View.ReaderInfo.class, View.AdminInfo.class})
    private LocalDateTime dateTo;

    public TicketDto(Ticket ticket) {
        id = ticket.getId();
        bookName = ticket.getBook().getName();
        readerName = ticket.getReader().getName() + " " +  ticket.getReader().getSurname();
        dateFrom = ticket.getDateFrom();
        dateTo = ticket.getDateTo();
    }
}