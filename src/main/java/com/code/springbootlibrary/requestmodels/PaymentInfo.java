package com.code.springbootlibrary.requestmodels;

import lombok.Data;

@Data
public class PaymentInfo {


    private double amount;

    private String currency;

    private String receiptEmail;

}
