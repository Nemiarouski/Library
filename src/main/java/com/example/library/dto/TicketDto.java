package com.example.library.dto;

import com.example.library.model.Ticket;
import com.fasterxml.jackson.annotation.JsonView;

public class TicketDto {
    private Long id;
    @JsonView(View.ReaderInfo.class)
    private String bookName;
    private String readerName;
    @JsonView(View.ReaderInfo.class)
    private String dateFrom;
    @JsonView(View.ReaderInfo.class)
    private String dateTo;

    public TicketDto() {}
    public TicketDto(Ticket ticket) {
        id = ticket.getId();
        bookName = ticket.getBookId().getName();
        readerName = ticket.getReaderId().getName() + " " +  ticket.getReaderId().getSurname();
        dateFrom = ticket.getDateFrom();
        dateTo = ticket.getDateTo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", readerName='" + readerName + '\'' +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}