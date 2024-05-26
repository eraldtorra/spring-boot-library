package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.ForumMessagesRepository;
import com.code.springbootlibrary.dao.ThreadsRepository;
import com.code.springbootlibrary.entity.ForumMessages;
import com.code.springbootlibrary.entity.Threads;
import com.code.springbootlibrary.requestmodels.AddForumMessages;
import com.code.springbootlibrary.requestmodels.AddThreads;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThreadService {


    private ThreadsRepository threadsRepository;

    private ForumMessagesRepository forumMessagesRepository;

    public ThreadService(ThreadsRepository threadsRepository, ForumMessagesRepository forumMessagesRepository) {
        this.threadsRepository = threadsRepository;
        this.forumMessagesRepository = forumMessagesRepository;
    }




    public void addThread(AddThreads addThreads, String userEmail) {
        Threads threads = new Threads();
        threads.setTitle(addThreads.getTitle());
        threads.setCreator(userEmail);
        threadsRepository.save(threads);
    }


    // add forum message to a thread
    public void addForumMessage(AddForumMessages addForumMessages, String userEmail) {
        ForumMessages forumMessages = new ForumMessages();
        forumMessages.setContent(addForumMessages.getContent());
        forumMessages.setPostedBy(userEmail);
        Threads threads = threadsRepository.findById(addForumMessages.getPostedIn()).get();
        forumMessages.setThread(threads);
        forumMessagesRepository.save(forumMessages);
    }
}
