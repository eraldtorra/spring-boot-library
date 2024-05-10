package com.code.springbootlibrary.dao;

import com.code.springbootlibrary.entity.ForumMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;


public interface ForumMessagesRepository extends JpaRepository<ForumMessages, Long> {
}
