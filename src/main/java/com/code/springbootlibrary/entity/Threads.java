package com.code.springbootlibrary.entity;

import jdk.jfr.Timestamp;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "threads")
@Data
public class Threads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String Title;

    @Column(name = "created_by")
    private String Creator;

    @Column(name = "creation_date")
    @CreationTimestamp
    private Date Date;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private Set<ForumMessages> messages;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Title == null) ? 0 : Title.hashCode());
        result = prime * result + ((Creator == null) ? 0 : Creator.hashCode());
        result = prime * result + ((Date == null) ? 0 : Date.hashCode());
        return result;
    }

}
