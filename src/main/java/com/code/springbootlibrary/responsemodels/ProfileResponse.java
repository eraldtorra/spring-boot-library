package com.code.springbootlibrary.responsemodels;

import lombok.Data;

@Data
public class ProfileResponse {

    private String FirstName;
    private String LastName;
    private String Email;
    private String Username;
}
