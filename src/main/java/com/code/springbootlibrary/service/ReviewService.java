package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.BookRepository;
import com.code.springbootlibrary.dao.ReviewRepository;
import com.code.springbootlibrary.entity.Review;
import com.code.springbootlibrary.requestmodels.ReviewRequest;

import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Clients;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.UserApi;
import org.openapitools.client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Logger;


@Service
@Transactional
public class ReviewService {




    private ReviewRepository reviewRepository;


    @Autowired
    public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndBookId(userEmail, reviewRequest.getBookId());

        if (validateReview != null){
            throw new Exception("Review already exists");
        }

        Review review = new Review();
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);

        if(reviewRequest.getReviewDescription().isPresent()){
            review.setReviewDescription(reviewRequest.getReviewDescription().map(
                    Object::toString).orElse(null));
        }

        review.setDate(Date.valueOf(LocalDate.now()));

        reviewRepository.save(review);

    }

    public boolean userReviewExists(String userEmail, Long bookId){
        Review review = reviewRepository.findByUserEmailAndBookId(userEmail, bookId);
        return review != null;
    }




}
