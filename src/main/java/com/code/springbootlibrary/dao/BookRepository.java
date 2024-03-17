package com.code.springbootlibrary.dao;

import com.code.springbootlibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource
public interface BookRepository extends JpaRepository<Book, Long>{
}
