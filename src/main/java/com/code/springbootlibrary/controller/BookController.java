package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.entity.Book;
import com.code.springbootlibrary.responsemodels.ShelfResponse;
import com.code.springbootlibrary.service.BookService;
import com.code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/checkedout/byuser")
    public Boolean CheckedOutByUser( @RequestParam Long bookId,
                                     @RequestHeader(value = "Authorization") String token){
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/checkout")
    public Book CheckoutBook(@RequestParam Long bookId,
                             @RequestHeader(value = "Authorization") String token) throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook( userEmail, bookId);
    }

    @GetMapping("/secure/checkedout/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfResponse> currentLoans(@RequestHeader(value = "Authorization") String token) throws ParseException {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoans(userEmail);
    }

}
