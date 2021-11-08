package com.example.library.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
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

    public Book() {}
    public Book(String name, String author, String year, Long amount) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.amount = amount;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(author, book.author) && Objects.equals(year, book.year) && Objects.equals(amount, book.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, year, amount);
    }

    @Override
    public int compareTo(Book o) {
        if (this.name.equals(o.getName())) {
            return 0;
        } else if (this.getName().compareTo(o.getName()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
}