package com.example.library.dto;

import com.example.library.model.Book;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private Long id;
    @JsonView(View.AdminInfo.class)
    private String name;
    private String author;
    private String year;
    private Long amount;
    private Long used;

    public BookDto(Book book, Long count) {
        long bookCount = 0;
        if(count != null) {
            bookCount = count;
        }
        id = book.getId();
        name = book.getName();
        author = book.getAuthor();
        year = book.getYear();
        amount = book.getAmount() + bookCount;
        used = bookCount;
    }
}