package com.code.springbootlibrary.responsemodels;

import com.code.springbootlibrary.entity.Book;
import lombok.Data;

@Data
public class ShelfResponse {

    private Book book;

    private int daysLeft;

    public ShelfResponse(Book book, int daysLeft) {
        this.book = book;
        this.daysLeft = daysLeft;
    }
}
