package com.example.library.dto;

import com.example.library.model.Ticket;

public class TicketDto {
    private Long id;
    private String bookName;
    private String readerName;
    private String dateFrom;
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