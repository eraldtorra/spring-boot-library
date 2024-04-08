package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.BookRepository;
import com.code.springbootlibrary.dao.CheckoutRepository;
import com.code.springbootlibrary.dao.HistoryRepository;
import com.code.springbootlibrary.entity.Book;
import com.code.springbootlibrary.entity.Checkout;
import com.code.springbootlibrary.entity.History;
import com.code.springbootlibrary.responsemodels.ShelfResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private HistoryRepository historyRepository;

    private CheckoutRepository checkoutRepository;


    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
    }


    public Book checkoutBook(String userEmail, Long bookId) throws Exception {
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


    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        Checkout validCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        return validCheckout != null;
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfResponse> currentLoans(String userEmail) throws ParseException {
        List<ShelfResponse> shelfResponses = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);

        List<Long> bookIdList = new ArrayList<>();

        for (Checkout checkout : checkoutList) {
            bookIdList.add(checkout.getBookId());
        }

        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Book book : books){
            Optional<Checkout> checkout = checkoutList
                    .stream().
                    filter(c -> c.getBookId().equals(book.getId())).findFirst();

            if (checkout.isPresent()){
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit timeUnit = TimeUnit.DAYS;

                long diff = timeUnit.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

                shelfResponses.add(new ShelfResponse(book, (int) diff));
            }
        }
        return shelfResponses;
    }

    public void returnBook(String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout checkout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || checkout == null) {
            throw new Exception("Book doesn't exist or not checked out");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        bookRepository.save(book.get());

        checkoutRepository.deleteById(checkout.getId());

        History history = new History(userEmail,
                checkout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg());

        historyRepository.save(history);



    }

    public void renewLoan(String userEmail, Long bookId) throws Exception{

        Checkout checkout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (checkout == null){
            throw new Exception("Book not checked out");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdf.parse(checkout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0){
            checkout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(checkout);
        } else {
            throw new Exception("Book cannot be renewed");
        }
    }



}
