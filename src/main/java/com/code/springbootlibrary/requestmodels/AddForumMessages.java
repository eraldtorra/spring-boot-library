package com.code.springbootlibrary.requestmodels;

import lombok.Data;

@Data
public class AddForumMessages {

    private String content;

    private Long postedIn;
}
