package com.example.library.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInformation extends BookDto {
    @JsonView(View.AdminInfo.class)
    private List<BookDto> freeBooks;
    @JsonView(View.AdminInfo.class)
    private List<BookDto> busyBooks;
}