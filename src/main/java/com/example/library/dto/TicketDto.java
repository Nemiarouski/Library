package com.example.library.dto;

import com.example.library.model.Ticket;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class TicketDto {
    private Long id;
    @JsonView(View.ReaderInfo.class)
    private String bookName;
    @JsonView(View.AdminInfo.class)
    private String readerName;
    @JsonView({View.ReaderInfo.class, View.AdminInfo.class})
    private String dateFrom;
    @JsonView({View.ReaderInfo.class, View.AdminInfo.class})
    private String dateTo;

    public TicketDto(Ticket ticket) {
        id = ticket.getId();
        bookName = ticket.getBook().getName();
        readerName = ticket.getReader().getName() + " " +  ticket.getReader().getSurname();
        dateFrom = ticket.getDateFrom();
        dateTo = ticket.getDateTo();
    }
}