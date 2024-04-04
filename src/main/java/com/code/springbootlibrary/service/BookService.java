package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.BookRepository;
import com.code.springbootlibrary.dao.CheckoutRepository;
import com.code.springbootlibrary.entity.Book;
import com.code.springbootlibrary.entity.Checkout;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;


    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
    }


    public Book checkoutBook(String userEmail, Long bookId) throws Exception  {
        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(), book.get().getId());

        checkoutRepository.save(checkout);

        return book.get();
    }


    public Boolean checkoutBookByUser(String userEmail, Long bookId){
        Checkout validCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validCheckout != null;
    }

    public int currentLoansCount(String userEmail){
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }
}
