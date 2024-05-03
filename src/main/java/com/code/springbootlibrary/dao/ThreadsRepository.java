package com.code.springbootlibrary.dao;


import com.code.springbootlibrary.entity.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface ThreadsRepository extends JpaRepository<Threads, Long> {
}

