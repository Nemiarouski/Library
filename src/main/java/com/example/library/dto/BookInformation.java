package com.example.library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

public class BookInformation extends BookDto {
    @JsonView(View.AdminInfo.class)
    private List<BookDto> freeBooks;
    @JsonView(View.AdminInfo.class)
    private List<BookDto> busyBooks;

    public BookInformation() {}
    public BookInformation(List<BookDto> freeBooks, List<BookDto> busyBooks) {
        this.freeBooks = freeBooks;
        this.busyBooks = busyBooks;
    }

    public List<BookDto> getFreeBooks() {
        return freeBooks;
    }

    public void setFreeBooks(List<BookDto> freeBooks) {
        this.freeBooks = freeBooks;
    }

    public List<BookDto> getBusyBooks() {
        return busyBooks;
    }

    public void setBusyBooks(List<BookDto> busyBooks) {
        this.busyBooks = busyBooks;
    }

    @Override
    public String toString() {
        return "BookInformation{" +
                "freeBooks=" + freeBooks +
                ", busyBooks=" + busyBooks +
                '}';
    }
}