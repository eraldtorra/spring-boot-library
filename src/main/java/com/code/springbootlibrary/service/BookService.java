package com.code.springbootlibrary.service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.code.springbootlibrary.dao.BookRepository;
import com.code.springbootlibrary.dao.CheckoutRepository;
import com.code.springbootlibrary.dao.HistoryRepository;

import com.code.springbootlibrary.dao.PaymentRepository;
import com.code.springbootlibrary.entity.Book;
import com.code.springbootlibrary.entity.History;
import com.code.springbootlibrary.entity.Checkout;
import com.code.springbootlibrary.entity.Payment;
import com.code.springbootlibrary.responsemodels.ShelfResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    private PaymentRepository paymentRepository;

    public BookService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                       HistoryRepository historyRepository, PaymentRepository paymentRepository) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
        this.paymentRepository = paymentRepository;
    }

    public Book checkoutBook (String userEmail, Long bookId) throws Exception {

        Optional<Book> book = bookRepository.findById(bookId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        if (!book.isPresent() || validateCheckout != null || book.get().getCopiesAvailable() <= 0) {
            throw new Exception("Book doesn't exist or already checked out by user");
        }

        List<Checkout> currentBooksCheckedOut = checkoutRepository.findBooksByUserEmail(userEmail);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        boolean bookNeedsReturned = false;

        for (Checkout checkout: currentBooksCheckedOut) {
            Date d1 = sdf.parse(checkout.getReturnDate());
            Date d2 = sdf.parse(LocalDate.now().toString());

            TimeUnit time = TimeUnit.DAYS;

            double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

            if (differenceInTime < 0) {
                bookNeedsReturned = true;
                break;
            }
        }

        Payment userPayment = paymentRepository.findByUserEmail(userEmail);

        if ((userPayment != null && userPayment.getAmount() > 0) || (userPayment != null && bookNeedsReturned)) {
            throw new Exception("Outstanding fees");
        }

        if (userPayment == null) {
            Payment payment = new Payment();
            payment.setAmount(00.00);
            payment.setUserEmail(userEmail);
            paymentRepository.save(payment);
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);
        bookRepository.save(book.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                book.get().getId()
        );

        checkoutRepository.save(checkout);

        return book.get();
    }

    public Boolean checkoutBookByUser(String userEmail, Long bookId) {
        // Find the checkout by the user's email and book ID
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);
        // Check if the checkout exists
        if (validateCheckout!= null) {
            return true;
        } else {
            return false;
        }
    }
    public int currentLoansCount(String userEmail) {
        // Get the number of books in the user's shelf
        return checkoutRepository.findBooksByUserEmail(userEmail).size();
    }

    public List<ShelfResponse> currentLoans(String userEmail) throws Exception {

        // Create an empty list to store the response objects
        List<ShelfResponse> shelfCurrentLoansResponses = new ArrayList<>();

        // Get the list of books from the user's shelf
        List<Checkout> checkoutList = checkoutRepository.findBooksByUserEmail(userEmail);
        List<Long> bookIdList = new ArrayList<>();

        // Create a list of book ids
        for (Checkout i: checkoutList) {
            bookIdList.add(i.getBookId());
        }

        // Get the list of books from the database
        List<Book> books = bookRepository.findBooksByBookIds(bookIdList);

        // Create a SimpleDateFormat object to parse the return date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Create a list of books
        for (Book book : books) {
            // Get the checkout object for the current book
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getBookId() == book.getId()).findFirst();

            // If the checkout object is present
            if (checkout.isPresent()) {

                // Parse the return date
                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                // Calculate the difference in time
                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                // Add the response object to the list
                shelfCurrentLoansResponses.add(new ShelfResponse(book, (int) difference_In_Time));
            }
        }
        // Return the list of response objects
        return shelfCurrentLoansResponses;
    }
    public void returnBook (String userEmail, Long bookId) throws Exception {

        //Find the book by its ID
        Optional<Book> book = bookRepository.findById(bookId);

        //Find the checkout by the user email and book ID
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        //Check if the book is present or not
        if (!book.isPresent() || validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        //Update the number of copies available
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);

        //Save the book
        bookRepository.save(book.get());

        //Create a SimpleDateFormat object
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //Parse the return date and current date
        Date d1 = sdf.parse(validateCheckout.getReturnDate());
        Date d2 = sdf.parse(LocalDate.now().toString());

        //Calculate the difference in time
        TimeUnit time = TimeUnit.DAYS;

        //Calculate the difference in time
        double differenceInTime = time.convert(d1.getTime() - d2.getTime(), TimeUnit.MILLISECONDS);

        //Check if the difference in time is less than 0
        if (differenceInTime < 0) {
            //Find the payment by the user email or create a new payment object
            Payment payment = paymentRepository.findByUserEmail(userEmail);

            if (payment == null) {
                payment = new Payment();
                payment.setUserEmail(userEmail);
                payment.setAmount(00.00);
            }

            //Update the amount of the payment
            payment.setAmount(payment.getAmount() + (differenceInTime * -1));
            //Save the payment
            paymentRepository.save(payment);
        }

        //Delete the checkout
        checkoutRepository.deleteById(validateCheckout.getId());

        //Create a new history object
        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                book.get().getTitle(),
                book.get().getAuthor(),
                book.get().getDescription(),
                book.get().getImg()
        );

        //Save the history
        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long bookId) throws Exception {

        //Find the checkout by the user email and book ID
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndBookId(userEmail, bookId);

        //Check if the checkout is present or not
        if (validateCheckout == null) {
            throw new Exception("Book does not exist or not checked out by user");
        }

        //Create a SimpleDateFormat object
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Parse the return date and current date
        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        //Check if the return date is greater than the current date
        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            //Update the return date to the next week
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            //Save the checkout
            checkoutRepository.save(validateCheckout);
        }
    }
}
