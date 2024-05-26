package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.requestmodels.AddForumMessages;
import com.code.springbootlibrary.requestmodels.AddThreads;
import com.code.springbootlibrary.service.ThreadService;
import com.code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/api/threads/secure")
public class ThreadController {


    private ThreadService threadService;

    @Autowired
    public ThreadController(ThreadService threadService) {
        this.threadService = threadService;
    }


    @PostMapping("/add")
    public void addThread(@RequestHeader(value = "Authorization") String token
            , @RequestBody AddThreads addThreads) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");


        threadService.addThread(addThreads, userEmail);

    }

    @PostMapping("/add/message")
    public void addForumMessage(@RequestHeader(value = "Authorization") String token
            , @RequestBody AddForumMessages addForumMessages) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        threadService.addForumMessage(addForumMessages, userEmail);
    }


}
