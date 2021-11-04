package com.example.library.dto;

import com.example.library.model.Book;
import com.fasterxml.jackson.annotation.JsonView;

public class BookDto {
    private Long id;
    @JsonView(View.AdminInfo.class)
    private String name;
    private String author;
    private String year;
    private Long amount;
    private Long used;

    public BookDto() {}
    public BookDto(Book book, Long count) {
        id = book.getId();
        name = book.getName();
        author = book.getAuthor();
        year = book.getYear();
        amount = book.getAmount() + count;
        used = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", amount=" + amount +
                ", used=" + used +
                '}';
    }
}