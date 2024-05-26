package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.requestmodels.ReviewRequest;
import com.code.springbootlibrary.service.ReviewService;
import com.code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping("/secure/post")
    public void postReview(@RequestHeader(value = "Authorization") String token,
                           @RequestBody ReviewRequest reviewRequest)throws Exception{
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null){
            throw new Exception("User not found");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }

    @GetMapping("/secure/userReviewExists")
    public boolean userReviewExists(@RequestHeader(value = "Authorization") String token,
                                    @RequestParam Long bookId) throws Exception{


        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null){
            throw new Exception("User not found");
        }
        return reviewService.userReviewExists(userEmail, bookId);
    }

}
