package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.BookRepository;
import com.code.springbootlibrary.dao.CheckoutRepository;
import com.code.springbootlibrary.dao.ReviewRepository;
import com.code.springbootlibrary.entity.Book;
import com.code.springbootlibrary.requestmodels.AddBookRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    private ReviewRepository reviewRepository;

    @Autowired
    public AdminService(BookRepository bookRepository, CheckoutRepository checkoutRepository, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
    }

    public void postBook(AddBookRequest addBookRequest) {

        Book book = new Book();
        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        bookRepository.save(book);

    }

    public void increaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            throw new Exception("Book not found");
        } else {
           book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
           book.get().setCopies(book.get().getCopies() + 1);

           bookRepository.save(book.get());
        }
    }

    public void decreaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book not found or no copies available");
        }

            book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
            book.get().setCopies(book.get().getCopies() - 1);

            bookRepository.save(book.get());

    }

    public void deleteBook(Long bookId) throws Exception {
       Optional<Book> book = bookRepository.findById(bookId);

         if (book.isEmpty()) {
             throw new Exception("Book not found");
         }
         checkoutRepository.deleteAllByBookId(bookId);
         reviewRepository.deleteAllByBookId(bookId);
         bookRepository.deleteById(bookId);
    }



}
