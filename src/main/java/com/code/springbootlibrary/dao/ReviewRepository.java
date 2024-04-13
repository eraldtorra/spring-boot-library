package com.code.springbootlibrary.dao;

import com.code.springbootlibrary.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.RequestParam;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review>  findByBookId(@RequestParam("book_id") Long bookId, Pageable pageable);

    Review findByUserEmailAndBookId(String userEmail, Long bookId);

    @Modifying
    @Query("delete from Review r where r.bookId = :bookId")
    public void deleteAllByBookId(@Param("bookId") Long bookId);
}
