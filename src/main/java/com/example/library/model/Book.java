package com.example.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book implements Comparable<Book> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "year")
    private String year;

    @Column(name = "amount")
    private Long amount;

    public Book(String name, String author, String year, Long amount) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.amount = amount;
    }

    @Override
    public int compareTo(Book o) {
        return this.name.compareTo(o.getName());
    }
}