package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.MessageRepository;
import com.code.springbootlibrary.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message reqMessage, String userEmail) {

        Message message = new Message(reqMessage.getTitle(), reqMessage.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);


    }



}
