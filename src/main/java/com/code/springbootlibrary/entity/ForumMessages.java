package com.code.springbootlibrary.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "forum")
@Data
public class ForumMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String Content;

    @Column(name = "posted_by")
    private String PostedBy;

    @Column(name = "posting_date")
    @CreationTimestamp
    private Date DatePosted;

    @ManyToOne
    @JoinColumn(name = "posted_in",nullable = false)
    private Threads thread;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Content == null) ? 0 : Content.hashCode());
        result = prime * result + ((PostedBy == null) ? 0 : PostedBy.hashCode());
        result = prime * result + ((DatePosted == null) ? 0 : DatePosted.hashCode());
        return result;
    }

}
