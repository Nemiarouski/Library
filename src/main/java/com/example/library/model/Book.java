package com.example.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 3, max = 30, message = "Name can be between 3 and 30 characters.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Author can't be empty.")
    @Size(min = 3, max = 30, message = "Author can be between 3 and 30 characters.")
    @Column(name = "author")
    private String author;

    @NotEmpty(message = "Year can't be empty.")
    @Min(value = 0, message = "Year can't be a negative number.")
    @Column(name = "year")
    private String year;

    @Min(value = 1, message = "Amount can't be less than 1.")
    @Column(name = "amount")
    private Long amount;

    public Book(String name, String author, String year, Long amount) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.amount = amount;
    }

    public void copyOf(Book book, Long count) {
        this.setName(book.getName());
        this.setAuthor(book.getAuthor());
        this.setYear(book.getYear());
        this.setAmount(book.getAmount() - count);
    }

    @Override
    public int compareTo(Book o) {
        return this.name.compareTo(o.getName());
    }
}