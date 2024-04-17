package com.code.springbootlibrary.service;

import com.code.springbootlibrary.dao.PaymentRepository;
import com.code.springbootlibrary.entity.Payment;
import com.code.springbootlibrary.requestmodels.PaymentInfo;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PaymentService {


    private PaymentRepository paymentRepository;


    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.secret.key}") String stripeSecretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = stripeSecretKey;
    }


    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws Exception {
        List<String> paymentMethodsTypes = new ArrayList<>();
        paymentMethodsTypes.add("card");

        Map<String,Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodsTypes);


        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if(payment == null){
            throw new Exception("Payment not found");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
